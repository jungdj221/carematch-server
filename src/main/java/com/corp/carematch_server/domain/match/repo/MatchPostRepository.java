package com.corp.carematch_server.domain.match.repo;

import com.corp.carematch_server.domain.match.entity.MatchPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchPostRepository extends JpaRepository<MatchPost, Long>{
}
