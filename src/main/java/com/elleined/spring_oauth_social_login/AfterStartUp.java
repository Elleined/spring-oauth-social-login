package com.elleined.spring_oauth_social_login;

import com.elleined.spring_oauth_social_login.repository.DBUserRepository;
import com.elleined.spring_oauth_social_login.service.db.DBUserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AfterStartUp {

    private final Faker faker;

    private final DBUserRepository dbUserRepository;
    private final DBUserService userService;

    @PostConstruct
    void addDefaultUsers() {
        if (dbUserRepository.existsById(1))
            return;

        log.debug("Saving pre-defined users! Please wait...");
        String email = faker.bothify("????#??@gmail.com");
        String name = faker.name().fullName();
        String image = faker.avatar().image();
        userService.save(email, "password", name, image);
        log.debug("Saving pre-defined users! Success...");
    }
}
