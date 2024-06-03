package com.elleined.spring_oauth_social_login.model.user;

import com.elleined.spring_oauth_social_login.model.authority.Authority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Entity
@Table(name = "tbl_db_user")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class DBUser extends User implements UserDetails {

    @Column(
            name = "password",
            nullable = false
    )
    private String password;

    @ManyToMany(mappedBy = "dbUsers")
    private Set<Authority> authorities;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }
}
