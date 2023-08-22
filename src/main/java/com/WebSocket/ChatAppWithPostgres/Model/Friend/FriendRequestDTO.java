package com.WebSocket.ChatAppWithPostgres.Model.Friend;

import com.WebSocket.ChatAppWithPostgres.Model.User.UserDTO;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendRequestDTO {
    private Integer id;
    private UserDTO requestSender;
    private UserDTO requestReceiver;
    private RequestStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
