package com.corp.carematch_server.domain.user.api;

import com.corp.carematch_server.domain.user.application.ProfileCommandService;
import com.corp.carematch_server.domain.user.application.ProfileQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-service/api/v1")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class ManagerController {

    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;
}
