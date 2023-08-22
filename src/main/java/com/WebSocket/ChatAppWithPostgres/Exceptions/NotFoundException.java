package com.WebSocket.ChatAppWithPostgres.Exceptions;

public class NotFoundException extends RuntimeException{

//    public NotFoundException(Integer id) {
//        super("Couldn't find the field with id " + id);
//    }

    public NotFoundException(String message) {
        super(message);
    }
}
