package com.elleined.spring_oauth_social_login.mapper;

import com.elleined.spring_oauth_social_login.model.Authority;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "users", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "authority", source = "authority")
    })
    Authority toEntity(String authority);
}
