package com.elleined.spring_oauth_social_login.exception.credential;

import com.elleined.spring_oauth_social_login.exception.SystemException;

public class CredentialException extends SystemException {
    public CredentialException(String message) {
        super(message);
    }
}
