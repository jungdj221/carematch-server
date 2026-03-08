package com.corp.carematch_server.domain.match.entity;

import com.corp.carematch_server.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name = "match_posts")
public class MatchPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_post_id")
    private Long matchPostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private User user; // 공고 작성자(신청자)

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "category")
    private Category category;

    @NotNull
    @Column(name = "title", length = 100)
    private String title;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "service_type")
    private ServiceType serviceType;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "status")
    private MatchStatus status;

    @Column(name = "start_main", length = 225)
    private String startMain;

    @Column(name = "start_detail", length = 225)
    private String startDetail;

    @Column(name = "dest_main", length = 225)
    private String destMain;

    @Column(name = "dest_detail", length = 225)
    private String destDetail;

    @Column(name = "total_amount")
    private Integer totalAmount;

    // --- 1:N 양방향 매핑 (여러 매니저의 지원 내역을 가짐) ---
    @Builder.Default
    @OneToMany(mappedBy = "matchPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostApplication> applications = new ArrayList<>(); // NPE 방지용 초기화

    public enum Category {
        일반, 긴급
    }

    public enum ServiceType {
        왕복, 편도, 병원내
    }

    public enum MatchStatus {
        WAITING, MATCHED, COMPLETED, FAILED
    }

    // 1:N 연관관계 편의 메서드 (매니저가 지원할 때 메모리 동기화)
    public void addApplication(PostApplication application) {
        this.applications.add(application);
        application.assignMatchPost(this);
    }
}
