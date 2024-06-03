package com.elleined.spring_oauth_social_login.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "tbl_authority")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Authority extends PrimaryKeyIdentity implements GrantedAuthority {

    @Column(
            name = "authority",
            nullable = false,
            unique = true,
            updatable = false
    )
    private String authority;

    @ManyToMany
    @JoinTable(
            name = "tbl_user_authority",
            joinColumns = @JoinColumn(
                    name = "authority_id",
                    referencedColumnName = "id",
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    private Set<User> users;

    @Override
    public String getAuthority() {
        return authority;
    }
}
