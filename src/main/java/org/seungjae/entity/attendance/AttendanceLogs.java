package org.seungjae.entity.attendance;

import jakarta.persistence.*;
import lombok.*;
import org.seungjae.entity.Schedules;
import org.seungjae.entity.Studies;
import org.seungjae.entity.Users;

import java.time.LocalDateTime;

@Entity
@Table(name = "attendance_logs")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AttendanceLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id", nullable = false)
    private Studies studyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedules scheduleId;

    @Column(name = "attended_at")
    private LocalDateTime attendedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AttendanceStatus status;

}
