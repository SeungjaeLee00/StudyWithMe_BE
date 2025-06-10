package org.seungjae.scheduler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.seungjae.repository.EmailVerificationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailVerifiCodeCleanupScheduler {

    private final EmailVerificationRepository emailVerificationRepository;

    @Transactional
    @Scheduled(fixedRate = 30 * 60 * 1000) // 30분마다 실행 (밀리초)
    public void deleteExpiredVerifications() {
        LocalDateTime now = LocalDateTime.now();
        emailVerificationRepository.deleteAllByExpiresAtBefore(now);
        log.info("만료된 이메일 인증 데이터를 삭제했습니다: {}", now);
    }
}
