package com.corp.carematch_server.domain.user.repo;

import com.corp.carematch_server.domain.user.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
