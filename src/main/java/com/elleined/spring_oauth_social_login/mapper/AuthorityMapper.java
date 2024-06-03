package com.elleined.spring_oauth_social_login.mapper;

import com.elleined.spring_oauth_social_login.model.authority.Authority;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "authority", source = "authority"),
            @Mapping(target = "dbUsers", expression = "java(new java.util.HashSet<>())"),
            @Mapping(target = "socialUsers", expression = "java(new java.util.HashSet<>())")
    })
    Authority toEntity(String authority);
}
