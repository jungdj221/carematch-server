package com.corp.carematch_server.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name = "patient_profiles")
public class PatientProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_profile_id")
    private Long patientProfileId;

    // 여러개의 patient_profile 이 하나의 profile 에 종속
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false) // FK 컬럼명 지정, 필수값이므로 nullable=false
    private Profile profile;

    @Column(name = "patient_name", length = 10)
    private String patientName;

    @Column(name = "patient_age")
    private Integer patientAge;

    @Enumerated(EnumType.STRING)
    @Column(name = "patient_gender")
    private Gender patientGender;

    @Column(name = "patient_condition", length = 1000)
    private String patientCondition;

    public enum Gender {
        M, F
    }

    // 연관관계 세팅 편의 메서드 (내부 캡슐화)
    public void assignProfile(Profile profile) {
        this.profile = profile;
    }
}
