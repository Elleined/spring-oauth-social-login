package com.elleined.spring_oauth_social_login.service.user;

import com.elleined.spring_oauth_social_login.exception.resource.ResourceNotFoundException;
import com.elleined.spring_oauth_social_login.mapper.UserMapper;
import com.elleined.spring_oauth_social_login.model.User;
import com.elleined.spring_oauth_social_login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import java.util.Optional;
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
            String password = UUID.randomUUID().toString();
            String name = oidcUser.getFullName();
            String picture = oidcUser.getPicture();
            Collection<? extends GrantedAuthority> authorities = oidcUser.getAuthorities();

            if (isEmailAlreadyExists(email))
                return getByEmail(email);

            return this.save(password, email, name, picture, authorities);
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
            String password = UUID.randomUUID().toString();
            String name = oAuth2User.getAttribute("name");
            String picture = oAuth2User.getAttribute("avatar_url");
            Collection<? extends GrantedAuthority> authorities = oAuth2User.getAuthorities();

            if (isEmailAlreadyExists(email))
                return getByEmail(email);

            return this.save(password, email, name, picture, authorities);
        };
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails currentUser = (UserDetails) authentication.getPrincipal();

        if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            throw new IllegalArgumentException("Old password is not correct!");
        }

        String email = currentUser.getUsername();
        userRepository.findByEmail(email)
                .ifPresent(cu -> cu.setPassword(passwordEncoder.encode(newPassword)));
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findAll().stream()
                .map(User::getEmail)
                .anyMatch(username::equalsIgnoreCase);
    }

    // username alias for id
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User with email of " + username + " doesn't exists!"));
    }


    @Override
    public void createUser(UserDetails userDetails) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateUser(UserDetails user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteUser(String username) {
        throw new UnsupportedOperationException();
    }


    @Override
    public User save(String password, String email, String name, String imageUrl, Collection<? extends GrantedAuthority> authorities) {
        User user = userMapper.toEntity(password, email, name, imageUrl, authorities);
        userRepository.save(user);
        log.debug("Saving user success");
        return user;
    }

    @Override
    public User save(String password, String email, String name, String imageUrl) {
        User user = userMapper.toEntity(password, email, name, imageUrl);
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
}
