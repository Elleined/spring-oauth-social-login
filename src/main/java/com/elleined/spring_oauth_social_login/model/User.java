package com.elleined.spring_oauth_social_login.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.*;

@Entity
@Table(name = "tbl_user")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class User extends PrimaryKeyIdentity implements UserDetails, OidcUser {

    @Column(
            name = "password",
            nullable = false
    )
    private String password;

    @Column(
            name = "email",
            nullable = false,
            unique = true
    )
    private String email;

    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @Column(
            name = "image",
            nullable = false
    )
    private String imageUrl;

    @ManyToMany(mappedBy = "users")
    private Set<Authority> authorities;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return new HashMap<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

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

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
        authority.getUsers().add(this);
    }

    public void mergeAuthorities(List<Authority> authorities) {
        this.authorities.addAll(authorities);
    }
}
