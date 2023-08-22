package com.WebSocket.ChatAppWithPostgres.Model.User;

import com.WebSocket.ChatAppWithPostgres.Model.Friend.FriendRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Integer id;
    private String userName;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Role role;
    private List<UserDTO> friends;
    private List<FriendRequest> receivedRequests;
}
