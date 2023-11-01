package com.WebSocket.ChatAppWithPostgres.Services;

import com.WebSocket.ChatAppWithPostgres.Exceptions.NotFoundException;
import com.WebSocket.ChatAppWithPostgres.Model.Message.Message;
import com.WebSocket.ChatAppWithPostgres.Model.Message.MessageDTO;
import com.WebSocket.ChatAppWithPostgres.Model.Message.MessageStatus;
import com.WebSocket.ChatAppWithPostgres.Model.User.User;
import com.WebSocket.ChatAppWithPostgres.Model.User.UserDTO;
import com.WebSocket.ChatAppWithPostgres.Repository.MessageRepository;
import com.WebSocket.ChatAppWithPostgres.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final UserService userService;
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


    //send message with old status
    public ApiResponse sendOldMessage(MessageDTO message, Integer senderId, Integer receiverId) {
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
                    .status(MessageStatus.OLD)
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

    public ApiResponse getUserAllMessagesIntoHashMap(Integer userId) {
        try {
            var get_friends = userService.getFriends(userId);

            List<UserDTO> friends = null;
            Map<String, Object> friend_messages = new HashMap<>();

            if (get_friends.isSuccess()) {
                Object result = get_friends.getResult();

                if (result instanceof List<?>) {
                    friends = (List<UserDTO>) result;
                    for(int i = 0; i < friends.toArray().length; i++) {
                        var user = friends.get(i);
                        friend_messages.put(user.getUserName(), this.getUserMessages(userId, user.getId()).getResult());
                    }
                }

                return ApiResponse.builder()
                        .result(friend_messages)
                        .message("Get Friends Messages Successfully")
                        .error(null)
                        .success(true)
                        .build();
            }
            return ApiResponse.builder()
                    .result(null)
                    .message("Get Friends Messages Failed")
                    .error("There is an error")
                    .success(false)
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .result(null)
                    .message("Get Friends Messages Failed")
                    .error(e.getMessage())
                    .success(false)
                    .build();
        }
    }
    public ApiResponse getUserMessages(Integer senderId, Integer receiverId) {
        try {
            var messages = messageRepository.findAllByUserId(senderId, receiverId);

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
