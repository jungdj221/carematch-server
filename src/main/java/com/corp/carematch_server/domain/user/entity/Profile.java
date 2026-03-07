package com.corp.carematch_server.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    @Column(name = "image_url", length = 100)
    private String imageUrl;

    @Column(name = "join_date", updatable = false)
    private LocalDateTime joinDate;

    @Column(name = "update_date")
    private LocalDateTime updatedAt;

    // --- 매니저는 여전히 1:1 관계 유지 ---
    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private ManagerProfile managerProfile;

    // --- 환자 프로필은 1:N 관계로 변경 ---
    @Builder.Default // 롬복 빌더 사용 시 리스트 초기화 무시되는 것 방어함
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatientProfile> patientProfiles = new ArrayList<>(); // NPE 방지용 빈 리스트 초기화 필수임

    public void addManagerProfile(ManagerProfile managerProfile) {
        // 1. 내(Profile) 필드에 자식을 세팅함
        this.managerProfile = managerProfile;
        // 2. 자식(ManagerProfile)에게도 나를 세팅해줌 (양방향 동기화)
        managerProfile.assignProfile(this);
    }

    // 1:N 연관관계 편의 메서드
    public void addPatientProfile(PatientProfile patientProfile) {
        this.patientProfiles.add(patientProfile);
        patientProfile.assignProfile(this); // 양방향 동기화
    }
}
