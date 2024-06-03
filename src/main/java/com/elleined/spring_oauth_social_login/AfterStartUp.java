package com.elleined.spring_oauth_social_login;

import com.elleined.spring_oauth_social_login.service.user.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AfterStartUp {

    private final UserService userService;

    @PostConstruct
    void addDefaultUsers() {
        if (userService.isEmailAlreadyExists("sample@gmail.com"))
            return;

        userService.save("sample@gmail.com", "sample", "Sample Name", "Sample Image");
    }
}