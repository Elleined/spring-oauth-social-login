package com.elleined.spring_oauth_social_login.service.user;

import com.elleined.spring_oauth_social_login.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public interface UserService extends UserDetailsManager {

    User save(String password,
                  String email,
                  String name,
                  String imageUrl,
                  Collection<? extends GrantedAuthority> authorities); // Save user from OAuth

    User save(String password,
                  String email,
                  String name,
                  String imageUrl); // Save user from Form

    User getByEmail(String email);
    boolean isEmailAlreadyExists(String email);

    default void saveOauth2User(User user, Executor executor) {
        CompletableFuture.runAsync(() -> createUser(user), executor);
    }
}
