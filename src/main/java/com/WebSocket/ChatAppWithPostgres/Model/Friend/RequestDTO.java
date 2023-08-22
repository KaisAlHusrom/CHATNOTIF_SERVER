package com.WebSocket.ChatAppWithPostgres.Model.Friend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestDTO {
    private Integer senderId;
    private Integer receiverId;
}
