package com.WebSocket.ChatAppWithPostgres.Exceptions;


import org.springframework.security.core.AuthenticationException;

public class InvalidJwtException extends AuthenticationException {
    public InvalidJwtException(String msg) {
        super(msg);
    }

    public InvalidJwtException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
