package com.corp.carematch_server.domain.auth.repo;

import com.corp.carematch_server.domain.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
