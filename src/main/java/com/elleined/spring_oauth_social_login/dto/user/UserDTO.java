package com.elleined.spring_oauth_social_login.dto.user;

import com.elleined.spring_oauth_social_login.dto.DTO;
import com.elleined.spring_oauth_social_login.dto.authority.AuthorityDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class UserDTO extends DTO {
    private String email;
    private String name;
    private String image;

    private Set<AuthorityDTO> authorityDTOS;
}
