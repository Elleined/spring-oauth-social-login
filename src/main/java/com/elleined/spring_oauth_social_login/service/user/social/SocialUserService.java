package com.elleined.spring_oauth_social_login.service.user.social;

import com.elleined.spring_oauth_social_login.model.user.SocialUser;
import com.elleined.spring_oauth_social_login.service.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;

public interface SocialUserService extends UserService<SocialUser> {

    SocialUser save(String email,
                    String name,
                    String image,
                    String socialId,
                    String nickname,
                    Collection<? extends GrantedAuthority> authorities,
                    SocialUser.Provider provider);

    @Bean
    OAuth2UserService<OidcUserRequest, OidcUser> oidcLoginHandler();

    @Bean
    OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2LoginHandler();

    @Override
    SocialUser getByEmail(String email);

    @Override
    boolean isEmailAlreadyExists(String email);

    default SocialUser.Provider getProvider(OAuth2UserRequest userRequest) {
        return SocialUser.Provider.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
    }

}
