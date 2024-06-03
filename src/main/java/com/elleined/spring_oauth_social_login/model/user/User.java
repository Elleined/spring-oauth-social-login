package com.elleined.spring_oauth_social_login.model.user;

import com.elleined.spring_oauth_social_login.model.PrimaryKeyIdentity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class User extends PrimaryKeyIdentity {

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

    @Column(name = "image")
    private String image;
}
