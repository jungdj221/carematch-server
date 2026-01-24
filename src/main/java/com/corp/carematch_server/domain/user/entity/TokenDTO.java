package com.corp.carematch_server.domain.user.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenDTO {

    private String accessToken;  // 나중에 Body 로 나갈 놈
    private String email;
    private String name;
    private String refreshToken; // 나중에 Cookie 로 나갈 놈
}
