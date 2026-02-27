package com.corp.carematch_server.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicInsert
@Builder
@Table(name = "credentials")
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "password_id")
    private Long passwordId;

    // TODO: 1:1 혹은 N:1 혹은 N:M 관계 설정 필요 (@OneToOne or @ManyToOne or etc...)
    // UNIQUE Key 존재 -> 1:1 관계
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false) // TODO: 참조하는 테이블의 컬럼명(snake_case) 확인 필요
    private User user;

    @Column(name = "salt", length = 128)
    private String salt;

    @Column(name = "password", length = 128)
    private String password;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

}
