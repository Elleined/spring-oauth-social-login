package com.elleined.spring_oauth_social_login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/form-login")
public class FormLoginController {

    @GetMapping("/success")
    public String goToFormLoginSuccessPage() {
        return "form-login-success";
    }
}
