package org.seungjae.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_verification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailVerification {

    @Id
    @Column(length = 255)
    private String email;

    @Column(nullable = false, length = 6)
    private String code; // 인증번호 (6자리 숫자 추천)

    @Column(nullable = false)
    private LocalDateTime expiresAt; // 만료시간

    @Column(nullable = false)
    private boolean verified; // 인증완료 여부

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 생성 시간

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
