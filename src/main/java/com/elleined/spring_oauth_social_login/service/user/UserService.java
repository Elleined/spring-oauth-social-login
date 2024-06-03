package com.elleined.spring_oauth_social_login.service.user;

import com.elleined.spring_oauth_social_login.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

public interface UserService extends UserDetailsService {

    User save(String email,
                  String name,
                  String imageUrl,
                  Collection<? extends GrantedAuthority> authorities); // Save user from OAuth

    User save(String email,
              String password,
              String name,
              String imageUrl); // Save user from Form

    User getByEmail(String email);
    boolean isEmailAlreadyExists(String email);
}
