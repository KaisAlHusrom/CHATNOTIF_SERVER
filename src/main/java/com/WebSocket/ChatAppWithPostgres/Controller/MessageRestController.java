package com.WebSocket.ChatAppWithPostgres.Controller;

import com.WebSocket.ChatAppWithPostgres.Model.Message.Message;
import com.WebSocket.ChatAppWithPostgres.Model.Message.MessageDTO;
import com.WebSocket.ChatAppWithPostgres.Repository.MessageRepository;
import com.WebSocket.ChatAppWithPostgres.Services.ApiResponse;
import com.WebSocket.ChatAppWithPostgres.Services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
public class MessageRestController {
    private final MessageService messageService;

    @PostMapping("/send-message/{senderId}/{receiverId}")
    public ResponseEntity<ApiResponse> sendMessage(
            @RequestBody MessageDTO message,
            @PathVariable Integer senderId,
            @PathVariable Integer receiverId
    ) {
        return ResponseEntity.ok(messageService.sendMessage(message, senderId, receiverId));
    }

    @PutMapping("/update-message-status-to-old/{messageId}")
    public ResponseEntity<ApiResponse> updateMessageStatusToOld(@PathVariable Integer messageId) {
        return ResponseEntity.ok(messageService.updateMessageStatusToOld(messageId));
    }

    @GetMapping("/get-user-messages/{senderId}")
    public ResponseEntity<ApiResponse> getUserMessages(
            @PathVariable Integer senderId
    ){
        return ResponseEntity.ok(messageService.getUserMessages(senderId));
    }
}
