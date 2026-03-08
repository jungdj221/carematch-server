package com.corp.carematch_server.domain.user.repo;

import com.corp.carematch_server.domain.user.entity.ManagerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerProfileRepository extends JpaRepository<ManagerProfile, Long> {
}
