package com.elleined.spring_oauth_social_login.dto.user;

import com.elleined.spring_oauth_social_login.dto.authority.AuthorityDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class DBUserDTO extends UserDTO implements UserDetails {

    @JsonIgnore
    private String password;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public Set<AuthorityDTO> getAuthorities() {
        return this.getAuthorityDTOS();
    }
}
