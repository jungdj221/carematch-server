package com.corp.carematch_server.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long userNo; // int unsigned -> Long 권장

    @Column(name = "user_name", length = 20)
    private String userName;

    // 암호화 필수 필드
    @Column(name = "email", nullable = false, length = 128)
    private String email;

    // 0: 이메일, 1: 카카오, 2: 네이버 등 (Enum 변환 고려)
    @Column(name = "login_type")
    private Integer loginType;

    @Column(name = "user_role")
    private String userRole;


}
