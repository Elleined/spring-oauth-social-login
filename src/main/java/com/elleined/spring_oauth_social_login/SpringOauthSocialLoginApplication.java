package com.elleined.spring_oauth_social_login;

import net.datafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringOauthSocialLoginApplication {

    public static void main(String[] args) {
		SpringApplication.run(SpringOauthSocialLoginApplication.class, args);
	}

	@Bean
	public Faker faker() {
		return new Faker();
	}
}
