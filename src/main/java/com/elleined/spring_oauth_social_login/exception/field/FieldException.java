package com.elleined.spring_oauth_social_login.exception.field;

import com.elleined.spring_oauth_social_login.exception.SystemException;

public class FieldException extends SystemException {
    public FieldException(String message) {
        super(message);
    }
}
