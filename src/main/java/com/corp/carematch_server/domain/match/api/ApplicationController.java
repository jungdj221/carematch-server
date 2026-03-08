package com.corp.carematch_server.domain.match.api;

import com.corp.carematch_server.domain.match.application.ApplicationCommandService;
import com.corp.carematch_server.domain.match.application.ApplicationQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/match-service/api/v1")
public class ApplicationController {

    private  final ApplicationCommandService applicationCommandService;
    private  final ApplicationQueryService applicationQueryService;
}
