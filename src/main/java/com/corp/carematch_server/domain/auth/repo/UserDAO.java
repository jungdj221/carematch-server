package com.corp.carematch_server.domain.auth.repo;

import com.corp.carematch_server.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);
}
