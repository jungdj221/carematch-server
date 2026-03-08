package com.corp.carematch_server.domain.match.repo;

import com.corp.carematch_server.domain.match.entity.PostApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostApplicationRepository extends JpaRepository<PostApplication, Long> {
}
