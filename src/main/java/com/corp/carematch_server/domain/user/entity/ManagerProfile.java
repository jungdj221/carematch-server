package com.corp.carematch_server.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
@Table(name = "manager_profiles")
public class ManagerProfile {

    @Id
    @Column(name = "profile_id")
    private Long profileId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column(name = "bio_content", length = 1000)
    private String bioContent;

    @Column(name = "career_info", length = 500)
    private String careerInfo;

    @Column(name = "active_region", length = 500)
    private String activeRegion;

    @Column(name = "is_premium_verified")
    private Boolean isPremiumVerified;

    public void assignProfile(Profile profile) {
        this.profile = profile;
    }
}
