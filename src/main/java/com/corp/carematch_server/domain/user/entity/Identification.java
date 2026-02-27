package com.corp.carematch_server.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
@Builder
@Table(name = "identifications")
public class Identification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authentication_id")
    private Long authenticationId;

    // TODO: 1:1 혹은 N:1 혹은 N:M 관계 설정 필요 (@OneToOne or @ManyToOne or etc...)
    // UNIQUE 제약조건이 있으므로 @OneToOne 권장
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no") // TODO: 참조하는 테이블의 컬럼명(snake_case) 확인 필요
    private User user;

    // 개인정보 수집 동의 여부 (TinyInt -> Boolean)
    @Column(name = "gather_agree")
    private Boolean gatherAgree;

    // 암호화 필수
    @Column(name = "cell_phone", nullable = false, length = 128)
    private String cellPhone;

    // 암호화 필수 (String으로 저장)
    @Column(name = "birthday", length = 128)
    private String birthday;

    // 0: 남성, 1: 여성 (Enum 권장)
    @Column(name = "sex")
    private Integer sex;

    // 0: 내국인, 1: 외국인 (Enum 권장)
    @Column(name = "nation")
    private Integer nation;

    @Column(name = "auth_date")
    private LocalDateTime authDate;
}
