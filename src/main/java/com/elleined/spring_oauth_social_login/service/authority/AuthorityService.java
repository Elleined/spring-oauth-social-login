package com.elleined.spring_oauth_social_login.service.authority;

import com.elleined.spring_oauth_social_login.model.authority.Authority;
import com.elleined.spring_oauth_social_login.model.user.User;

import java.util.List;

public interface AuthorityService<USER extends User> {
    Authority save(String authority);
    Authority getByAuthority(String authority);
    boolean isAlreadyExists(String name);
    void add(USER currentUser, Authority authority);
    void remove(USER currentUser, Authority authority);
    void addAll(USER currentUser, List<Authority> authorities); // merge
}
