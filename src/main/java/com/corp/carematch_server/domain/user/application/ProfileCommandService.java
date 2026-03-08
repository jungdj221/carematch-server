package com.corp.carematch_server.domain.user.application;

import com.corp.carematch_server.domain.user.repo.ManagerProfileRepository;
import com.corp.carematch_server.domain.user.repo.PatientProfileRepository;
import com.corp.carematch_server.domain.user.repo.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ProfileCommandService {

    private final ProfileRepository profileRepository;
    private final PatientProfileRepository patientProfileRepository;
    private final ManagerProfileRepository managerProfileRepository;
}
