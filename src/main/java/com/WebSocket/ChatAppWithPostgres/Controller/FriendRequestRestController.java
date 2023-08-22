package com.WebSocket.ChatAppWithPostgres.Controller;

import com.WebSocket.ChatAppWithPostgres.Model.Friend.RequestDTO;
import com.WebSocket.ChatAppWithPostgres.Services.ApiResponse;
import com.WebSocket.ChatAppWithPostgres.Services.FriendRequestService;
import jakarta.persistence.OrderBy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/friend-request")
public class FriendRequestRestController {
    private final FriendRequestService friendRequestService;
    @PostMapping("/add-friend-request")
    public ResponseEntity<ApiResponse> addRequestFriendWithPostMethod(@RequestBody RequestDTO requestDTO) {
        return ResponseEntity.ok(friendRequestService.addRequestFriend(requestDTO));
    }

    @PostMapping("/add-rejected-notification")
    public ResponseEntity<ApiResponse> addRejectedNotification(@RequestBody RequestDTO requestDTO) {
        return ResponseEntity.ok(friendRequestService.addRejectedNotification(requestDTO));
    }

    @GetMapping("/get-user_requests/{userId}")
    public ResponseEntity<ApiResponse> getUserRequests(@PathVariable Integer userId) {
        return ResponseEntity.ok(friendRequestService.getUserRequests(userId));
    }

    @GetMapping("/get-user-request/{senderId}/{receiverId}")
    public ResponseEntity<ApiResponse> getUserRequest(@PathVariable Integer senderId, @PathVariable Integer receiverId){
        return ResponseEntity.ok(friendRequestService.getUserRequest(senderId, receiverId));

    }

    @PutMapping("/update-request-status-accept/{reqId}")
    public ResponseEntity<ApiResponse> updateRequestStatusToAccept(@PathVariable Integer reqId) {
        return ResponseEntity.ok(friendRequestService.updateRequestStatusToAccept(reqId));
    }

    @PutMapping("/update-request-status-reject/{reqId}")
    public ResponseEntity<ApiResponse> updateRequestStatusToReject(@PathVariable Integer reqId) {
        return ResponseEntity.ok(friendRequestService.updateRequestStatusToReject(reqId));
    }

    @PutMapping("/update-request-status-old/{reqId}")
    public ResponseEntity<ApiResponse> updateRequestStatusToOld(@PathVariable Integer reqId) {
        return ResponseEntity.ok(friendRequestService.updateRequestStatusToOld(reqId));
    }
}
