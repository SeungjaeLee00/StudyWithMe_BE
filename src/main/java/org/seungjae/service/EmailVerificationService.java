package org.seungjae.service;

import lombok.RequiredArgsConstructor;
import org.seungjae.dto.ApiResponse;
import org.seungjae.entity.EmailVerification;
import org.seungjae.repository.EmailVerificationRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;
    private final JavaMailSender mailSender;

    /**
     * 인증번호 발송
     * - 최근 1분 이내에 발급한 인증번호가 있으면 재발송 제한
     * - 6자리 인증번호 생성 및 저장
     * - 이메일 전송
     */
    public ApiResponse<Void> sendVerificationCode(String email) {
        // 최근 발급된 인증번호 조회
        EmailVerification existingVerification = emailVerificationRepository.findByEmail(email).orElse(null);

        if (existingVerification != null) {
            // 1분 이내 재발송 제한
            LocalDateTime oneMinuteAgo = LocalDateTime.now().minusMinutes(1);
            if (existingVerification.getCreatedAt().isAfter(oneMinuteAgo)) {
                return new ApiResponse<>(false, "인증번호는 1분에 한 번만 재발송할 수 있습니다.");
            }
        }

        // 6자리 랜덤 인증번호 생성 (000000 ~ 999999)
        String verificationCode = String.format("%06d", new Random().nextInt(1000000));
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(10);

        EmailVerification verification = (existingVerification != null) ? existingVerification : EmailVerification.builder().email(email).build();

        verification.setCode(verificationCode);
        verification.setExpiresAt(expiresAt);
        verification.setVerified(false);
        verification.setCreatedAt(LocalDateTime.now());

        emailVerificationRepository.save(verification);

        // 이메일 전송
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("스터디윗미 이메일 인증 코드");
        message.setText("인증번호는 " + verificationCode + "입니다. 10분 내에 입력해주세요.");
        mailSender.send(message);

        return new ApiResponse<>(true, "인증번호가 이메일로 전송되었습니다.");
    }

    /**
     * 인증번호 검증
     */
    public ApiResponse<Void> verifyCode(String email, String code) {
        EmailVerification verification = emailVerificationRepository.findByEmail(email).orElse(null);

        if (verification == null) {
            return new ApiResponse<>(false, "인증 요청이 존재하지 않습니다.");
        }

        if (verification.isVerified()) {
            return new ApiResponse<>(false, "이미 인증된 이메일입니다.");
        }

        if (!verification.getCode().equals(code)) {
            return new ApiResponse<>(false, "인증번호가 일치하지 않습니다.");
        }

        if (LocalDateTime.now().isAfter(verification.getExpiresAt())) {
            return new ApiResponse<>(false, "인증번호가 만료되었습니다.");
        }

        verification.setVerified(true);
        emailVerificationRepository.save(verification);

        return new ApiResponse<>(true, "이메일 인증이 완료되었습니다.");
    }
}
