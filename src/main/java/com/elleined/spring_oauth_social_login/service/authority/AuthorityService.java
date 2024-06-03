package com.elleined.spring_oauth_social_login.service.authority;

import com.elleined.spring_oauth_social_login.model.Authority;
import com.elleined.spring_oauth_social_login.model.User;

import java.util.List;

public interface AuthorityService {
    Authority save(String authority);
    Authority getByAuthority(String authority);
    boolean isAlreadyExists(String name);
    void add(User currentUser, Authority authority);
    void remove(User currentUser, Authority authority);
    void addAll(User currentUser, List<Authority> authorities); // merge
}
