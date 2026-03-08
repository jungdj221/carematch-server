package com.corp.carematch_server.domain.user.repo;

import com.corp.carematch_server.domain.user.entity.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long> {
}
