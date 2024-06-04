package com.elleined.spring_oauth_social_login.mapper;

import com.elleined.spring_oauth_social_login.dto.authority.AuthorityDTO;
import com.elleined.spring_oauth_social_login.dto.user.DBUserDTO;
import com.elleined.spring_oauth_social_login.dto.user.SocialUserDTO;
import com.elleined.spring_oauth_social_login.model.user.DBUser;
import com.elleined.spring_oauth_social_login.model.user.SocialUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = AuthorityMapper.class)
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "image", source = "image"),
            @Mapping(target = "password", source = "password")
    })
    DBUser toEntity(String email,
                    String password,
                    String name,
                    String image);

    @Mappings({
            @Mapping(target = "id", source = "dbUser.id"),
            @Mapping(target = "createdAt", source = "dbUser.createdAt"),
            @Mapping(target = "email", source = "dbUser.email"),
            @Mapping(target = "name", source = "dbUser.name"),
            @Mapping(target = "image", source = "dbUser.image"),
            @Mapping(target = "authorityDTOS", source = "authorityDTOS"),
            @Mapping(target = "password", source = "dbUser.password")
    })
    DBUserDTO toDTO(DBUser dbUser,
                    List<AuthorityDTO> authorityDTOS);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "image", source = "image"),
            @Mapping(target = "socialId", source = "socialId"),
            @Mapping(target = "nickname", source = "nickname"),
            @Mapping(target = "provider", source = "provider")
    })
    SocialUser toEntity(String email,
                        String name,
                        String image,
                        String socialId,
                        String nickname,
                        SocialUser.Provider provider);

    @Mappings({
            @Mapping(target = "id", source = "socialUser.id"),
            @Mapping(target = "createdAt", source = "socialUser.createdAt"),
            @Mapping(target = "email", source = "socialUser.email"),
            @Mapping(target = "name", source = "socialUser.name"),
            @Mapping(target = "image", source = "socialUser.image"),
            @Mapping(target = "authorityDTOS", source = "authorityDTOS"),
            @Mapping(target = "socialId", source = "socialUser.socialId"),
            @Mapping(target = "nickname", source = "socialUser.nickname"),
            @Mapping(target = "provider", source = "socialUser.provider")
    })
    SocialUserDTO toDTO(SocialUser socialUser,
                        List<AuthorityDTO> authorityDTOS);
}
