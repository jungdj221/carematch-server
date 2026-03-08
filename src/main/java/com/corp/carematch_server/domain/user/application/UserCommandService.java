package com.corp.carematch_server.domain.user.application;

import com.corp.carematch_server.domain.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserCommandService {

    private final UserRepository userRepository;
}
