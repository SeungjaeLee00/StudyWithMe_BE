package org.seungjae.entity.studyApplications;

import jakarta.persistence.*;
import lombok.*;
import org.seungjae.entity.Studies;
import org.seungjae.entity.Users;

import java.time.LocalDateTime;

@Entity
@Table(name = "study_applications")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class StudyApplications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", nullable = false)
    private Studies studyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ApplicationStatus status;

    @Column(name = "applied_at", nullable = false)
    private LocalDateTime appliedAt;
}
