package com.elleined.spring_oauth_social_login.dto.authority;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class AuthorityDTO implements GrantedAuthority {
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
