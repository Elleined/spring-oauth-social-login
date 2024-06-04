package com.elleined.spring_oauth_social_login.model.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tbl_social_user")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class SocialUser extends User {

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

    @Column(
            name = "provider",
            nullable = false,
            updatable = false
    )
    @Enumerated(EnumType.STRING)
    private Provider provider;

    public enum Provider {
        GITHUB, GOOGLE, FACEBOOK, TWITTER, INSTAGRAM
    }
}
