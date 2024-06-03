package com.elleined.spring_oauth_social_login.service.user.db;

import com.elleined.spring_oauth_social_login.model.user.DBUser;
import com.elleined.spring_oauth_social_login.service.user.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface DBUserService extends UserService<DBUser>, UserDetailsService {

    DBUser save(String email,
                String password,
                String name,
                String image);

    @Override
    DBUser getByEmail(String email);

    @Override
    boolean isEmailAlreadyExists(String email);

    @Override
    DBUser loadUserByUsername(String username) throws UsernameNotFoundException;
}
