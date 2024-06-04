package com.elleined.spring_oauth_social_login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/social-login")
public class SocialLoginController {

    @GetMapping("/success")
    public String goToSocialLoginSuccessPage() {
        return "social-login-success";
    }
}
