package com.elleined.spring_oauth_social_login.service.user;

import com.elleined.spring_oauth_social_login.exception.resource.ResourceNotFoundException;
import com.elleined.spring_oauth_social_login.mapper.UserMapper;
import com.elleined.spring_oauth_social_login.model.User;
import com.elleined.spring_oauth_social_login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final DefaultOAuth2UserService oAuth2UserService;
    private final OidcUserService oidcUserService;

    /**
     * Adapts oidc login to return User instead of default OidcUser
     *
     * @return service that returns User from request to the Oidc UserInfo Endpoint
     */
    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcLoginHandler() {
        return userRequest -> {
            OidcUser oidcUser = oidcUserService.loadUser(userRequest);
            log.debug("OIDC handled the request!");
            System.out.println(oidcUser);

            String email = oidcUser.getEmail();
            String name = oidcUser.getFullName();
            String picture = oidcUser.getPicture();
            Collection<? extends GrantedAuthority> authorities = oidcUser.getAuthorities();

            if (isEmailAlreadyExists(email))
                return getByEmail(email);

            return this.save(email, name, picture, authorities);
        };
    }

    /**
     * Adapts oauth2 login to return User instead of default OAauth2User
     *
     * @return service that returns User from request to the Oauth2 user info
     */
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2LoginHandler() {
        return userRequest -> {
            OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
            log.debug("OAuth2 handled the request!");
            System.out.println(oAuth2User);

            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");
            String picture = oAuth2User.getAttribute("avatar_url");
            Collection<? extends GrantedAuthority> authorities = oAuth2User.getAuthorities();

            if (isEmailAlreadyExists(email))
                return getByEmail(email);

            return this.save(email, name, picture, authorities);
        };
    }

    @Override
    public User save(String email, String name, String imageUrl, Collection<? extends GrantedAuthority> authorities) {
        String encodedPassword = passwordEncoder.encode(UUID.randomUUID().toString());
        User user = userMapper.toEntity(email, encodedPassword, name, imageUrl, authorities);
        userRepository.save(user);
        log.debug("Saving user success");
        return user;
    }

    @Override
    public User save(String email, String password, String name, String imageUrl) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = userMapper.toEntity(email, encodedPassword, name, imageUrl);
        userRepository.save(user);
        log.debug("Saving user success");
        return user;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User with email of " + email + " doesn't exists!"));
    }

    @Override
    public boolean isEmailAlreadyExists(String email) {
        return userRepository.findAll().stream()
                .map(User::getEmail)
                .anyMatch(email::equalsIgnoreCase);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User with email of " + username + " doesn't exists!"));
    }
}
