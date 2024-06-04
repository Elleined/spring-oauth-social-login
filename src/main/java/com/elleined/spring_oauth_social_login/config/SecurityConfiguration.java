package com.elleined.spring_oauth_social_login.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Slf4j
public class SecurityConfiguration {

    @Bean
    @Order(0)
    SecurityFilterChain resources(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/images/**", "/**.css", "/**.js")
                .authorizeHttpRequests(c -> c.anyRequest().permitAll())
                .securityContext(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .requestCache(RequestCacheConfigurer::disable)
                .build();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2LoginHandler,
                                            OAuth2UserService<OidcUserRequest, OidcUser> oidcLoginHandler) throws Exception {
        return http
                .formLogin(c -> c.defaultSuccessUrl("/form-login/success"))
                .oauth2Login(oc -> oc
                        .userInfoEndpoint(ui -> ui
                                .userService(oauth2LoginHandler)
                                .oidcUserService(oidcLoginHandler))
                        .defaultSuccessUrl("/social-login/success"))
                .authorizeHttpRequests(c -> c.requestMatchers(EndpointRequest.to("info", "health", "prometheus")).permitAll()
                        .requestMatchers(EndpointRequest.toAnyEndpoint().excluding("info", "health", "prometheus")).hasAuthority("manage")
                        .requestMatchers("/", "/login", "/user/sign-up", "/error").permitAll()
                        .anyRequest().authenticated())
                .build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successLogger() {
        return event -> log.info("success: {}", event.getAuthentication());
    }

    @Bean
    public DefaultOAuth2UserService defaultOAuth2UserService() {
        return new DefaultOAuth2UserService();
    }

    @Bean
    public OidcUserService oidcUserService() {
        return new OidcUserService();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http,
//                                            OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2LoginHandler,
//                                            OAuth2UserService<OidcUserRequest, OidcUser> oidcLoginHandler) throws Exception {
//        return http
//                .formLogin(c -> c.loginPage("/login")
//                        .successHandler()
//                        .loginProcessingUrl("/authenticate")
//                        .usernameParameter("user")
//                        .passwordParameter("pass")
//                        .defaultSuccessUrl("/user")
//                )
//                .logout(c -> c.logoutSuccessUrl("/?logout"))
//                .oauth2Login(oc -> oc
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/user")
//                        .userInfoEndpoint(ui -> ui
//                                .userService(oauth2LoginHandler)
//                                .oidcUserService(oidcLoginHandler)))
//                .authorizeHttpRequests(c -> c
//                        .requestMatchers(EndpointRequest.to("info", "health", "prometheus")).permitAll()
//                        .requestMatchers(EndpointRequest.toAnyEndpoint().excluding("info", "health", "prometheus")).hasAuthority("manage")
//                        .requestMatchers("/", "/login", "/user/sign-up", "/error").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .build();
//    }
}
