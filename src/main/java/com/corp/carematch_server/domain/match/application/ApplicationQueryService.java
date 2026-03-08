package com.corp.carematch_server.domain.match.application;

import com.corp.carematch_server.domain.match.repo.PostApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationQueryService {

    private  final PostApplicationRepository postApplicationRepository;
}
