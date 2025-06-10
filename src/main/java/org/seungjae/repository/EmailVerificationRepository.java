package org.seungjae.repository;

import org.seungjae.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, String> {
    Optional<EmailVerification> findByEmail(String email);  // 이메일 인증 여부를 확인할 때 사용
    void deleteAllByExpiresAtBefore(LocalDateTime time); // 스케줄러에서 10분 지난 인증번호들을 주기적으로 삭제할 때 사용.
}
