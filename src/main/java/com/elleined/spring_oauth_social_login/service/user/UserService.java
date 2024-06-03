package com.elleined.spring_oauth_social_login.service.user;

import com.elleined.spring_oauth_social_login.model.user.User;

public interface UserService<USER extends User> {
    USER getByEmail(String email);
    boolean isEmailAlreadyExists(String email);
}
