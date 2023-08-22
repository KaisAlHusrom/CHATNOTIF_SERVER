package com.WebSocket.ChatAppWithPostgres.Controller;

import com.WebSocket.ChatAppWithPostgres.Model.Friend.RequestDTO;
import com.WebSocket.ChatAppWithPostgres.Services.ApiResponse;
import com.WebSocket.ChatAppWithPostgres.Services.FriendRequestService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller

@RequiredArgsConstructor
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    @MessageMapping("/send-notif")
    @SendTo("/user-notification/send-notif")
    public ApiResponse addRequestFriend(@Payload ApiResponse response) {

        return response;
    }

    @MessageMapping("/accept-friend")
    @SendTo("/user-notification/accept-friend")
    public ApiResponse addFriend(@Payload ApiResponse response) {

        return response;
    }

    @MessageMapping("/reject-friend")
    @SendTo("/user-notification/reject-friend")
    public ApiResponse rejectFriend(@Payload ApiResponse response) {

        return response;
    }

    @MessageMapping("/remove-friend")
    @SendTo("/user-notification/remove-friend")
    public ApiResponse removeFriend(@Payload ApiResponse response) {

        return response;
    }

//    @MessageMapping("/get-notif")
//    @SendTo("/user-notification/get-notif")
//    public Request getRequests(@Payload Request request) {
//
//        return request;
//    }


}
