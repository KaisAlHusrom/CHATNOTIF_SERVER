package com.WebSocket.ChatAppWithPostgres.Services;

import com.WebSocket.ChatAppWithPostgres.Exceptions.NotFoundException;
import com.WebSocket.ChatAppWithPostgres.Model.Message.Message;
import com.WebSocket.ChatAppWithPostgres.Model.Message.MessageDTO;
import com.WebSocket.ChatAppWithPostgres.Model.Message.MessageStatus;
import com.WebSocket.ChatAppWithPostgres.Model.User.User;
import com.WebSocket.ChatAppWithPostgres.Repository.MessageRepository;
import com.WebSocket.ChatAppWithPostgres.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    //Send Message
    public ApiResponse sendMessage(
            MessageDTO message,
            Integer senderId,
            Integer receiverId
    ) {

        try {
            var sender = userRepository.findById(senderId).orElseThrow(
                    () -> new NotFoundException("There is no user with id: " + senderId)
            );
            var receiver = userRepository.findById(receiverId).orElseThrow(
                    () -> new NotFoundException("There is no user with id: " + receiverId)
            );

            var messageEntity = Message.builder()
                    .messageContent(message.getMessageContent())
                    .sender(sender)
                    .receiver(receiver)
                    .status(MessageStatus.NEW)
                    .build();

            messageEntity.getSender().getSentMessages().add(messageEntity);
            messageEntity.getReceiver().getReceivedMessages().add(messageEntity);

            userRepository.save(messageEntity.getSender());
            userRepository.save(messageEntity.getReceiver());
            return ApiResponse.builder()
                    .success(true)
                    .error(null)
                    .message("Message Sent Successfully")
                    .result(messageRepository.save(messageEntity))
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .success(false)
                    .error(e.getMessage())
                    .message("Message didn't send")
                    .result(null)
                    .build();
        }
    }

    public ApiResponse updateMessageStatusToOld(Integer messageId) {
        try {
            var message = messageRepository.findById(messageId).orElseThrow(
                    () -> new NotFoundException("There is no message with id: "+messageId)
            );

            message.setStatus(MessageStatus.OLD);


            return ApiResponse.builder()
                    .success(true)
                    .error(null)
                    .message("Message Updated Successfully")
                    .result(messageRepository.save(message))
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .success(false)
                    .error(e.getMessage())
                    .message("Message didn't Update")
                    .result(null)
                    .build();
        }
    }

    public ApiResponse getUserMessages(Integer senderId) {
        try {
            var messages = messageRepository.findAllByUserId(senderId);

            return ApiResponse.builder()
                    .success(true)
                    .error(null)
                    .message("Get Messages Successfully")
                    .result(messages)
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .success(false)
                    .error(e.getMessage())
                    .message("Get Messages Failed")
                    .result(null)
                    .build();
        }
    }
}
