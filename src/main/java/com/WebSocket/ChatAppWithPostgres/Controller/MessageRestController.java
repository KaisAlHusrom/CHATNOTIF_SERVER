package com.WebSocket.ChatAppWithPostgres.Controller;

import com.WebSocket.ChatAppWithPostgres.Model.Message.Message;
import com.WebSocket.ChatAppWithPostgres.Model.Message.MessageDTO;
import com.WebSocket.ChatAppWithPostgres.Model.Message.MessageToUpdate;
import com.WebSocket.ChatAppWithPostgres.Repository.MessageRepository;
import com.WebSocket.ChatAppWithPostgres.Services.ApiResponse;
import com.WebSocket.ChatAppWithPostgres.Services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/send-old-message/{senderId}/{receiverId}")
    public ResponseEntity<ApiResponse> sendOldMessage(
            @RequestBody MessageDTO message,
            @PathVariable Integer senderId,
            @PathVariable Integer receiverId
    ) {
        return ResponseEntity.ok(messageService.sendOldMessage(message, senderId, receiverId));
    }

    @PutMapping("/update-message-status-to-old/{messageId}")
    public ResponseEntity<ApiResponse> updateMessageStatusToOld(@PathVariable Integer messageId) {
        return ResponseEntity.ok(messageService.updateMessageStatusToOld(messageId));
    }

    @GetMapping("/get-user-messages/{senderId}/{receiverId}")
    public ResponseEntity<ApiResponse> getUserMessages(
            @PathVariable Integer senderId,
            @PathVariable Integer receiverId

    ){
        return ResponseEntity.ok(messageService.getUserMessages(senderId, receiverId));
    }

    @GetMapping("/get-user-messages-into-hash-map/{userId}")
    public ResponseEntity<ApiResponse> getUserAllMessagesIntoHashMap(@PathVariable Integer userId) {
        return ResponseEntity.ok(messageService.getUserAllMessagesIntoHashMap(userId));
    }
}
