package com.elleined.spring_oauth_social_login.mapper;

import com.elleined.spring_oauth_social_login.model.user.DBUser;
import com.elleined.spring_oauth_social_login.model.user.SocialUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "image", source = "image"),
            @Mapping(target = "authorities", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "password", source = "password")
    })
    DBUser toEntity(String email,
                    String password,
                    String name,
                    String image);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "image", source = "image"),
            @Mapping(target = "authorities", source = "authorities"),
            @Mapping(target = "socialId", source = "socialId"),
            @Mapping(target = "nickname", source = "nickname"),
            @Mapping(target = "provider", source = "provider")
    })
    SocialUser toEntity(String email,
                        String name,
                        String image,
                        String socialId,
                        String nickname,
                        Collection<? extends GrantedAuthority> authorities,
                        SocialUser.Provider provider);
}
