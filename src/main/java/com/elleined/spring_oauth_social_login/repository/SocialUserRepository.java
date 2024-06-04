package com.elleined.spring_oauth_social_login.repository;

import com.elleined.spring_oauth_social_login.model.user.SocialUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SocialUserRepository extends UserRepository<SocialUser> {

    @Query("SELECT u FROM SocialUser u WHERE u.email = :email")
    Optional<SocialUser> findByEmail(@Param("email") String email);
}