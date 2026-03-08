package com.corp.carematch_server.domain.user.api;
import com.corp.carematch_server.domain.user.application.UserCommandService;
import com.corp.carematch_server.domain.user.application.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-service/api/v1")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    /*
        USR-101
        USR-102
        USR-103
        USR-104
        USR-105
        USR-106
        USR-107
        USR-108
        USER-109& (AUT-901)
        USR-110
    */



}
