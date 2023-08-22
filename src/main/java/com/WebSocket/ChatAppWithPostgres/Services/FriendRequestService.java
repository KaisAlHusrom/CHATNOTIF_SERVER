package com.WebSocket.ChatAppWithPostgres.Services;

import com.WebSocket.ChatAppWithPostgres.Exceptions.NotFoundException;
import com.WebSocket.ChatAppWithPostgres.Model.Friend.FriendRequest;
import com.WebSocket.ChatAppWithPostgres.Model.Friend.RequestDTO;
import com.WebSocket.ChatAppWithPostgres.Model.Friend.RequestStatus;
import com.WebSocket.ChatAppWithPostgres.Repository.FriendRequestRepository;
import com.WebSocket.ChatAppWithPostgres.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendRequestService {
    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;

    public ApiResponse addRequestFriend(RequestDTO request) {
        try {
            var sender = userRepository.findById(request.getSenderId()).orElseThrow(
                    () -> new NotFoundException("There is no user with id: " + request.getSenderId())
            );
            var receiver = userRepository.findById(request.getReceiverId()).orElseThrow(
                    () -> new NotFoundException("There is no user with id: " + request.getReceiverId())
            );

            // Initialize the collections within the session
//            sender.getSentRequests().size();
//            receiver.getReceivedRequests().size();


            var req = FriendRequest.builder()
                    .requestSender(sender)
                    .requestReceiver(receiver)
                    .content(sender.getUsername() + " Send friend request for you")
                    .status(RequestStatus.PENDING_APPROVAL)
                    .build();

            sender.getSentRequests().add(req);
            receiver.getReceivedRequests().add(req);

            userRepository.save(sender);
            userRepository.save(receiver);


            return ApiResponse.builder()
                    .success(true)
                    .result(friendRequestRepository.save(req))
                    .error(null)
                    .message("Request Added Successfully")
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .success(false)
                    .result(null)
                    .error(e.getMessage())
                    .message("Request Added Failed")
                    .build();
        }


    }

    public ApiResponse addRejectedNotification(RequestDTO request) {
        try {
            var sender = userRepository.findById(request.getSenderId()).orElseThrow(
                    () -> new NotFoundException("There is no user with id: " + request.getSenderId())
            );
            var receiver = userRepository.findById(request.getReceiverId()).orElseThrow(
                    () -> new NotFoundException("There is no user with id: " + request.getReceiverId())
            );


            var req = FriendRequest.builder()
                    .requestSender(sender)
                    .requestReceiver(receiver)
                    .content(sender.getUsername() + " Rejected your friend request")
                    .status(RequestStatus.NEW)
                    .build();

            sender.getSentRequests().add(req);
            receiver.getReceivedRequests().add(req);

            userRepository.save(sender);
            userRepository.save(receiver);


            return ApiResponse.builder()
                    .success(true)
                    .result(friendRequestRepository.save(req))
                    .error(null)
                    .message("Notification Added Successfully")
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .success(false)
                    .result(null)
                    .error(e.getMessage())
                    .message("Notification Added Failed")
                    .build();
        }
    }
    public ApiResponse getUserRequests(Integer userId) {
        try {
            List<FriendRequest> requests = friendRequestRepository.findAllByRequestReceiverId(userId);

            return ApiResponse.builder()
                    .success(true)
                    .message("Get Requests Successfully")
                    .result(requests)
                    .error(null)
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .success(false)
                    .result(null)
                    .error(e.getMessage())
                    .message("Get Requests Failed")
                    .build();
        }
    }

    public ApiResponse getUserRequest(Integer senderId, Integer receiverId) {
        try {
            var req = friendRequestRepository.findSpecificRequest(senderId, receiverId);

            return ApiResponse.builder()
                    .success(true)
                    .message("Get Request Successfully")
                    .result(req)
                    .error(null)
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .success(false)
                    .result(null)
                    .error(e.getMessage())
                    .message("Get Request Failed")
                    .build();
        }
    }

    public ApiResponse updateRequestStatusToAccept(Integer reqId) {
        try {
            var req = friendRequestRepository.findById(reqId).orElseThrow(
                    ()-> new NotFoundException("There is no request with this id: "+ reqId)
            );

            req.setStatus(RequestStatus.ACCEPTED);

            return ApiResponse.builder()
                    .success(true)
                    .error(null)
                    .result(friendRequestRepository.save(req))
                    .message("Request status change to accepted successfully")
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .success(false)
                    .result(null)
                    .error(e.getMessage())
                    .message("Request status didn't change")
                    .build();
        }

    }

    public ApiResponse updateRequestStatusToReject(Integer reqId) {
        try {
            var req = friendRequestRepository.findById(reqId).orElseThrow(
                    ()-> new NotFoundException("There is no request with this id: "+ reqId)
            );

            req.setStatus(RequestStatus.REJECTED);

            return ApiResponse.builder()
                    .success(true)
                    .error(null)
                    .result(friendRequestRepository.save(req))
                    .message("Request status change to accepted successfully")
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .success(false)
                    .result(null)
                    .error(e.getMessage())
                    .message("Request status didn't change")
                    .build();
        }
    }

    public ApiResponse updateRequestStatusToOld(Integer reqId) {
        try {
            var req = friendRequestRepository.findById(reqId).orElseThrow(
                    ()-> new NotFoundException("There is no request with this id: "+ reqId)
            );

            req.setStatus(RequestStatus.OLD);

            return ApiResponse.builder()
                    .success(true)
                    .error(null)
                    .result(friendRequestRepository.save(req))
                    .message("Request status change to accepted successfully")
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .success(false)
                    .result(null)
                    .error(e.getMessage())
                    .message("Request status didn't change")
                    .build();
        }
    }
}
