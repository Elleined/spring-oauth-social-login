package com.elleined.spring_oauth_social_login.repository.user;

import com.elleined.spring_oauth_social_login.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, Integer> {
}