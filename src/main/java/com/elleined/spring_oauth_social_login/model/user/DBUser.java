package com.elleined.spring_oauth_social_login.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tbl_db_user")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class DBUser extends User {

    @Column(
            name = "password",
            nullable = false
    )
    private String password;
}
