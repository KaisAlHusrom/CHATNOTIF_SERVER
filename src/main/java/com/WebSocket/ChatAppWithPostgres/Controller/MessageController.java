package com.WebSocket.ChatAppWithPostgres.Controller;

import com.WebSocket.ChatAppWithPostgres.Model.Message.Message;
import com.WebSocket.ChatAppWithPostgres.Model.User.User;
import com.WebSocket.ChatAppWithPostgres.Repository.MessageRepository;
import com.WebSocket.ChatAppWithPostgres.Services.ApiResponse;
import com.WebSocket.ChatAppWithPostgres.Services.MessageService;
import com.WebSocket.ChatAppWithPostgres.Services.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;
    private final UserService userService;

    @MessageMapping("/send-message")
    @SendTo("/user-message/send-message")
    public ApiResponse sendPrivateMessage(@Payload ApiResponse apiResponse) {
        return apiResponse;
    }

}
