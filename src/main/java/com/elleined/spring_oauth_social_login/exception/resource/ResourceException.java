package com.elleined.spring_oauth_social_login.exception.resource;

import com.elleined.spring_oauth_social_login.exception.SystemException;

public class ResourceException extends SystemException {
    public ResourceException(String message) {
        super(message);
    }
}
