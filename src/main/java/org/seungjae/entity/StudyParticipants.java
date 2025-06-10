package org.seungjae.entity;

import jakarta.persistence.*;
import lombok.*;
import org.seungjae.entity.role.Roles;

import java.time.LocalDateTime;

@Entity
@Table(name = "study_participants")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class StudyParticipants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Integer participantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", nullable = false)
    private Studies studyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Roles roleId;

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;
}
