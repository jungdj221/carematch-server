package com.corp.carematch_server.domain.match.application;

import com.corp.carematch_server.domain.match.repo.MatchPostRepository;
import com.corp.carematch_server.domain.match.repo.PostApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MatchCommandService {

    private final MatchPostRepository matchPostRepository;
    private  final PostApplicationRepository postApplicationRepository;
}
