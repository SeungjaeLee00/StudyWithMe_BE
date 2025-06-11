package org.seungjae.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.seungjae.dto.UserLoginRequestDto;
import org.seungjae.dto.UserSignupRequestDto;
import org.seungjae.entity.EmailVerification;
import org.seungjae.entity.Users;
import org.seungjae.exception.CustomException;
import org.seungjae.repository.EmailVerificationRepository;
import org.seungjae.repository.UsersRepository;
import org.seungjae.security.JwtTokenProvider;
import org.seungjae.security.JwtAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    public void signup(UserSignupRequestDto dto) {
        if (usersRepository.existsByUsername(dto.getUsername())) {
            throw new CustomException("이미 사용 중인 사용자명입니다.");
        }

        if (usersRepository.existsByEmail(dto.getEmail())) {
            throw new CustomException("이미 사용 중인 이메일입니다.");
        }

        EmailVerification verification = emailVerificationRepository.findById(dto.getEmail())
                .orElseThrow(() -> new CustomException("이메일 인증이 필요합니다."));

        if (!verification.isVerified()) {
            throw new CustomException("이메일 인증이 완료되지 않았습니다.");
        }

        Users user = Users.builder()
                .id(generateUserId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .createdAt(LocalDateTime.now())
                .build();

        usersRepository.save(user);
    }

    private String generateUserId() {
        return "user_" + System.currentTimeMillis();
    }

    // 로그인
    public void login(UserLoginRequestDto dto, HttpServletResponse response) {
        Users user = usersRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new CustomException("가입되지 않은 이메일입니다."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new CustomException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user.getId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());

        // Refresh Token DB 저장
        user.setRefreshToken(refreshToken);
        usersRepository.save(user);

        // 쿠키 생성 및 응답에 담기
        setTokenCookie(response, "accessToken", accessToken, 15 * 60); // 15분
        setTokenCookie(response, "refreshToken", refreshToken, 4 * 60 * 60); // 4시간
    }

    private void setTokenCookie(HttpServletResponse response, String name, String value, int maxAgeSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // HTTPS일 때만 전송 (개발 중엔 false 가능)
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeSeconds);
        response.addCookie(cookie);
    }

    // 로그아웃
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // 쿠키에서 refreshToken 가져오기
        String refreshToken = JwtAuthenticationFilter.getJwtFromRequest(request);

        if (StringUtils.hasText(refreshToken)) {
            String userId = jwtTokenProvider.getEmailFromToken(refreshToken);
            removeRefreshToken(userId);
        }

        // 쿠키 삭제
        deleteCookie(response, "accessToken");
        deleteCookie(response, "refreshToken");
    }

    private void removeRefreshToken(String userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new CustomException("사용자를 찾을 수 없습니다."));
        user.setRefreshToken(null);
        usersRepository.save(user);
    }

    private void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 즉시 삭제
        response.addCookie(cookie);
    }

}
