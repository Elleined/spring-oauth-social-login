package com.elleined.spring_oauth_social_login.dto.authority;

import com.elleined.spring_oauth_social_login.dto.DTO;
import com.elleined.spring_oauth_social_login.model.user.DBUser;
import com.elleined.spring_oauth_social_login.model.user.SocialUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class AuthorityDTO extends DTO implements GrantedAuthority {
    private String authority;
    private Set<Integer> dbUserIds;
    private Set<Integer> socialUserIds;

    @Override
    public String getAuthority() {
        return authority;
    }
}
