package com.elleined.spring_oauth_social_login.repository.authority;

import com.elleined.spring_oauth_social_login.model.authority.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    @Query("SELECT a FROM Authority a WHERE a = :authority")
    Optional<Authority> findByAuthority(@Param("authority") String authority);
}