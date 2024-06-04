package com.elleined.spring_oauth_social_login.dto.user;

import com.elleined.spring_oauth_social_login.dto.authority.AuthorityDTO;
import com.elleined.spring_oauth_social_login.model.authority.Authority;
import com.elleined.spring_oauth_social_login.model.user.SocialUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class SocialUserDTO extends UserDTO implements OidcUser {
    private String socialId;
    private String nickname; // username
    private SocialUser.Provider provider;

    @Override
    public Map<String, Object> getClaims() {
        return Map.of();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return new HashMap<>();
    }

    @Override
    public Set<AuthorityDTO> getAuthorities() {
        return this.getAuthorityDTOS();
    }
}
