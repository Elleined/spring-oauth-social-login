package com.elleined.spring_oauth_social_login.mapper;

import com.elleined.spring_oauth_social_login.dto.authority.AuthorityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {

    @Mappings({
            @Mapping(target = "authority", source = "authority"),
    })
    AuthorityDTO toDTO(String authority);
}
