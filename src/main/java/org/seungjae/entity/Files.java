package org.seungjae.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts post;

    @Column(name = "schedule_id")
    private Integer scheduleId;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "is_archived", nullable = false)
    private boolean isArchived = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "archived_at")
    private LocalDateTime archivedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
