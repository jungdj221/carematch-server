package com.corp.carematch_server.domain.auth.repo;

import com.corp.carematch_server.domain.auth.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordDAO extends JpaRepository<Credential,Long> {

    Optional<Credential> findByUser (Long userNo);
}
