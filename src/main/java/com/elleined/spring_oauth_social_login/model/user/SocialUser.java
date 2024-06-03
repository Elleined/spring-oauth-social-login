package com.elleined.spring_oauth_social_login.model.user;

import com.elleined.spring_oauth_social_login.model.authority.Authority;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "tbl_social_user")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class SocialUser extends User implements OidcUser {

    @Column(
            name = "social_id",
            nullable = false,
            updatable = false,
            unique = true
    )
    private String socialId;

    @Column(
            name = "social_nickname",
            nullable = false,
            updatable = false
    )
    private String nickname; // username

    @ManyToMany(mappedBy = "socialUsers")
    private Set<Authority> authorities;

    @Column(
            name = "provider",
            nullable = false,
            updatable = false
    )
    @Enumerated(EnumType.STRING)
    private Provider provider;

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

    public enum Provider {
        GITHUB, GOOGLE
    }
}
