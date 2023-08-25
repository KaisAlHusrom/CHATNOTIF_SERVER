package com.WebSocket.ChatAppWithPostgres.Controller;

import com.WebSocket.ChatAppWithPostgres.Auth.AuthenticationResponse;
import com.WebSocket.ChatAppWithPostgres.Services.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthWebSocketController {

    @MessageMapping("/authenticate")
    @SendTo("/user-auth/authenticate")
    public String authenticate(@Payload String response) {

        return response;
    }

}
