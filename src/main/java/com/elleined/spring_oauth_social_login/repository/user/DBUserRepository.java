package com.elleined.spring_oauth_social_login.repository.user;

import com.elleined.spring_oauth_social_login.model.user.DBUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DBUserRepository extends UserRepository<DBUser> {

    @Query("SELECT u FROM DBUser u WHERE u.email = :email")
    Optional<DBUser> findByEmail(@Param("email") String email);
}