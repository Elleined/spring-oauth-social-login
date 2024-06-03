package com.elleined.spring_oauth_social_login.exception.credential;

public class InvalidCredentialException extends CredentialException {
    public InvalidCredentialException(String message) {
        super(message);
    }
}
