package com.corp.carematch_server.domain.user.api;

import com.corp.carematch_server.domain.user.application.UserCommandService;
import com.corp.carematch_server.domain.user.dto.UserRequestDTO;
import com.corp.carematch_server.domain.user.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 회원가입.
@RestController
@RequestMapping("/user-service/api/v1")
@CrossOrigin(origins = {"*"}, maxAge = 6000)
public class UserController {

    @Autowired
    private UserCommandService userCommandService;

    // signup
    @PostMapping("/public/auth/signup")
    public ResponseEntity<UserResponseDTO> signup(@RequestBody UserRequestDTO dto){
        UserResponseDTO response = userCommandService.signup(dto);
        return  ResponseEntity.ok(response);
    }

}
