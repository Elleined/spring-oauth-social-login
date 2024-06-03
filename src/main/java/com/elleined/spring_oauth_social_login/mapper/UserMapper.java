package com.elleined.spring_oauth_social_login.mapper;

import com.elleined.spring_oauth_social_login.model.User;
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
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "imageUrl", source = "imageUrl"),
            @Mapping(target = "authorities", source = "authorities"),
    })
    User toEntity(String password,
                  String email,
                  String name,
                  String imageUrl,
                  Collection<? extends GrantedAuthority> authorities); // Save user from OAuth

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "password", source = "password"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "imageUrl", source = "imageUrl"),
            @Mapping(target = "authorities", expression = "java(new java.util.HashSet<>())"),
    })
    User toEntity(String password,
                  String email,
                  String name,
                  String imageUrl); // Save user from Form
}
