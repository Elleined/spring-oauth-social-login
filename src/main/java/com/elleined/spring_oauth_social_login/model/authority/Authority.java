package com.elleined.spring_oauth_social_login.model.authority;

import com.elleined.spring_oauth_social_login.model.PrimaryKeyIdentity;
import com.elleined.spring_oauth_social_login.model.user.DBUser;
import com.elleined.spring_oauth_social_login.model.user.SocialUser;
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
public class Authority extends PrimaryKeyIdentity {

    @Column(
            name = "authority",
            nullable = false,
            unique = true,
            updatable = false
    )
    private String authority;

    @ManyToMany
    @JoinTable(
            name = "tbl_db_user_authority",
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
    private Set<DBUser> dbUsers;

    @ManyToMany
    @JoinTable(
            name = "tbl_social_user_authority",
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
    private Set<SocialUser> socialUsers;
}
