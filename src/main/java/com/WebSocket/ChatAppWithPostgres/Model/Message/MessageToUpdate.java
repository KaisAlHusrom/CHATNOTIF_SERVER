package com.WebSocket.ChatAppWithPostgres.Model.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageToUpdate {
    private List<Message> messageList;
}
