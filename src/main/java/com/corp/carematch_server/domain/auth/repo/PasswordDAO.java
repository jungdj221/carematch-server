package com.corp.carematch_server.domain.auth.repo;

import com.corp.carematch_server.domain.auth.entity.Password;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordDAO extends JpaRepository<Password,Long> {

    Optional<Password> findByUser (Long userNo);
}
