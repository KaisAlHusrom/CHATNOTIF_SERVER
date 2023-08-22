package com.WebSocket.ChatAppWithPostgres.Exceptions;

import com.WebSocket.ChatAppWithPostgres.Auth.AuthenticationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public AuthenticationResponse userNotFoundExceptionHandler(NotFoundException exception) {
        return AuthenticationResponse
                .builder()
                .response(
                        Map.of(
                                "success", false,
                                "status", HttpStatus.UNAUTHORIZED,
                                "error", exception.getMessage()
                        )
                )
                .build();
    }

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public AuthenticationResponse handleAuthenticationException(Exception e) {
        return AuthenticationResponse
                .builder()
                .response(
                        Map.of(
                        "success", false,
                        "status", HttpStatus.UNAUTHORIZED,
                        "message", "user name or password is incorrect",
                        "error", e.getMessage()
                        )
                )
                .build();
    }

    @ExceptionHandler(AccountStatusException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, Object> handleAccountStatusException(AccountStatusException e) {
        return Map.of(
                "success", false,
                "status", HttpStatus.UNAUTHORIZED,
                "message", "User account is abnormal",
                "error", e.getMessage()
        );
    }

    @ExceptionHandler(InvalidJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, Object> handleInvalidJwtException(InvalidJwtException e) {
        return Map.of(
                "success", false,
                "status", HttpStatus.UNAUTHORIZED,
                "message", "The access token provided is expired, revoked, malformed, or invalid for other reasons",
                "error", e.getMessage()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, Object> handleAccessDeniedException(AccessDeniedException e) {
        return Map.of(
                "success", false,
                "status", HttpStatus.FORBIDDEN,
                "message", "No Permission",
                "error", e.getMessage()
        );
    }

    /**
     * Fallback handles any unhandled exceptions.
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AuthenticationResponse handleOtherExceptions(Exception e) {
        return AuthenticationResponse
                .builder()
                .response(
                        Map.of(
                                "success", false,
                                "status", HttpStatus.INTERNAL_SERVER_ERROR,
                                "message", "A server internal error",
                                "error", e.getMessage()
                        )
                )
                .build();
    }


}
