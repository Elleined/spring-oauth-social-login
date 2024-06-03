package com.elleined.spring_oauth_social_login.service.user.social;

import com.elleined.spring_oauth_social_login.exception.resource.ResourceNotFoundException;
import com.elleined.spring_oauth_social_login.mapper.UserMapper;
import com.elleined.spring_oauth_social_login.model.authority.Authority;
import com.elleined.spring_oauth_social_login.model.user.SocialUser;
import com.elleined.spring_oauth_social_login.model.user.User;
import com.elleined.spring_oauth_social_login.repository.user.SocialUserRepository;
import com.elleined.spring_oauth_social_login.service.authority.AuthorityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class SocialUserServiceImpl implements SocialUserService {

    private final AuthorityService<SocialUser> authorityService;

    private final SocialUserRepository userRepository;
    private final UserMapper userMapper;

    private final DefaultOAuth2UserService oAuth2UserService;
    private final OidcUserService oidcUserService;

    @Override
    public SocialUser save(String email, String name, String image, String socialId, String nickname, Collection<? extends GrantedAuthority> authorities, SocialUser.Provider provider) {
        SocialUser socialUser = userMapper.toEntity(email, name, image, socialId, nickname, authorities, provider);

        userRepository.save(socialUser);
        log.debug("Saving social user success");
        return socialUser;
    }

    /**
     * Adapts oidc login to return User instead of default OidcUser
     *
     * @return service that returns User from request to the Oidc UserInfo Endpoint
     */
    @Override
    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcLoginHandler() {
        return userRequest -> {
            OidcUser oidcUser = oidcUserService.loadUser(userRequest);
            log.debug("OIDC handled the request!");
            System.out.println(oidcUser);

            String email = oidcUser.getEmail();
            String name = oidcUser.getFullName();
            String image = oidcUser.getPicture();
            String socialId = oidcUser.getAttribute("id");
            String nickname = oidcUser.getPreferredUsername();
            Collection<? extends GrantedAuthority> authorities = oidcUser.getAuthorities();
            SocialUser.Provider provider = getProvider(userRequest);

            // save the authorities
            if (isEmailAlreadyExists(email))
                return getByEmail(email);

            return this.save(email, name, image, socialId, nickname, authorities, provider);
        };
    }

    /**
     * Adapts oauth2 login to return User instead of default OAauth2User
     *
     * @return service that returns User from request to the Oauth2 user info
     */
    @Override
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2LoginHandler() {
        return userRequest -> {
            OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
            log.debug("OAuth2 handled the request!");
            System.out.println(oAuth2User);

            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");
            String image = oAuth2User.getAttribute("avatar_url");
            String socialId = oAuth2User.getName();
            String nickname = oAuth2User.getAttribute("login");
            Collection<? extends GrantedAuthority> authorities = oAuth2User.getAuthorities();
            SocialUser.Provider provider = getProvider(userRequest);

            if (isEmailAlreadyExists(email))
                return this.getByEmail(email);

            return this.save(email, name, image, socialId, nickname, authorities, provider);
        };
    }

    @Override
    public SocialUser getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User with email of " + email + " doesn't exists!"));
    }

    @Override
    public boolean isEmailAlreadyExists(String email) {
        return userRepository.findAll().stream()
                .map(User::getEmail)
                .anyMatch(email::equalsIgnoreCase);
    }
}
