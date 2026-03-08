package com.corp.carematch_server.domain.match.entity;


import com.corp.carematch_server.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name = "post_applications")
public class PostApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long applicationId;

    // 어떤 공고글에 지원했는가 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_post_id", nullable = false)
    private MatchPost matchPost;

    // 누가 지원했는가 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private User applicant;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "apply_status")
    private ApplyStatus applyStatus;

    @Column(name = "applied_at", updatable = false)
    private LocalDateTime appliedAt;

    public enum ApplyStatus {
        PENDING, ACCEPTED, REJECTED
    }

    // 연관관계 캡슐화 메서드 (공고글 세팅)
    public void assignMatchPost(MatchPost matchPost) {
        this.matchPost = matchPost;
    }
}
