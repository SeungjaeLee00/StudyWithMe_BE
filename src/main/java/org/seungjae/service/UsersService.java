package org.seungjae.service;

import lombok.RequiredArgsConstructor;
import org.seungjae.dto.UserSignupRequestDto;
import org.seungjae.entity.EmailVerification;
import org.seungjae.entity.Users;
import org.seungjae.exception.CustomException;
import org.seungjae.repository.EmailVerificationRepository;
import org.seungjae.repository.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final BCryptPasswordEncoder passwordEncoder;

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
}
