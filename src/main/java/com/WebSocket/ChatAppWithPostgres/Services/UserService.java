package com.WebSocket.ChatAppWithPostgres.Services;

import com.WebSocket.ChatAppWithPostgres.Exceptions.NotFoundException;
import com.WebSocket.ChatAppWithPostgres.Model.User.User;
import com.WebSocket.ChatAppWithPostgres.Model.User.UserDTO;
import com.WebSocket.ChatAppWithPostgres.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;

    //Get All users Service
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();
        users.forEach(user -> {
            List<UserDTO> friendDTOs = toUserDTOList(user.getFriendWith());

            usersDTO.add(UserDTO.builder()
                    .id(user.getId())
                    .userName(user.getUsername())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt())
                    .receivedRequests(user.getReceivedRequests())
                    .friends(friendDTOs)
                    .build()
            );
        });
        return usersDTO;
    }

    public ApiResponse findUserById(Integer id) {
        try {
            var user = userRepo.findById(id).orElseThrow(() -> new NotFoundException("There is no user with this id: " + id));
            return ApiResponse.builder()
                    .success(true)
                    .message("get user successfully")
                    .result(user)
                    .error(null)
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .success(false)
                    .message("get user Failed")
                    .result(null)
                    .error(e.getMessage())
                    .build();
        }
    }

    public ApiResponse findAllByUserName(String userName) {
        try {
            List<User> users = userRepo.findAllByUserName(userName);
            List<UserDTO> usersDTO = toUserDTOList(users);

            return ApiResponse.builder()
                    .success(true)
                    .message("get users successfully")
                    .result(usersDTO)
                    .error(null)
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .success(false)
                    .message("get users Failed")
                    .result(null)
                    .error(e.getMessage())
                    .build();
        }
    }


    public ApiResponse removeFriend(Integer userId, Integer friendWithId) {
        try {
            var user = userRepo.findById(userId).orElseThrow(
                    () -> new NotFoundException("There is no user with id: " + userId)
            );

            var friend = userRepo.findById(friendWithId).orElseThrow(
                    () -> new NotFoundException("There is no user with id: " + friendWithId)
            );

            user.getFriendWith().remove(friend);    // Update user's side of the relationship
            friend.getFriendWith().remove(user);

            userRepo.save(user);
            userRepo.save(friend);

            return ApiResponse.builder()
                    .success(true)
                    .message("Friend Removed Successfully")
                    .error(null)
                    .result(toUserDTO(friend))
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .success(false)
                    .message("Friend Remove Failed")
                    .error(e.getMessage())
                    .result(null)
                    .build();
        }
    }

    public ApiResponse addFriend(Integer userId, Integer friendWith) {
        try {
            var user = userRepo.findById(userId).orElseThrow(
                    () -> new NotFoundException("There is no user with id: " + userId)
            );

            var friend = userRepo.findById(friendWith).orElseThrow(
                    () -> new NotFoundException("There is no user with id: " + friendWith)
            );



            user.getFriendWith().add(friend);    // Update user's side of the relationship
            friend.getFriendWith().add(user);    // Update friend's side of the relationship

            userRepo.save(user);
            userRepo.save(friend);

            return ApiResponse.builder()
                    .success(true)
                    .message("Friend Added Successfully")
                    .error(null)
                    .result(toUserDTO(friend))
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .success(false)
                    .message("Friend Added Failed")
                    .error(e.getMessage())
                    .result(null)
                    .build();
        }
    }


    public ApiResponse getFriends(Integer userId) {
        try {
            var user = userRepo.findById(userId).orElseThrow(
                    () -> new NotFoundException("There is no user with id: " + userId)
            );

            List<UserDTO> friends = toUserDTOList(user.getFriendWith());

            return ApiResponse.builder()
                    .success(true)
                    .message("Get Friends Successfully")
                    .result(friends)
                    .error(null)
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .success(false)
                    .message("get Friends Failed")
                    .result(null)
                    .error(e.getMessage())
                    .build();
        }


    }

    public UserDTO toUserDTO(User user) {

        return UserDTO.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .receivedRequests(user.getReceivedRequests())
                .build();

    }

    public List<UserDTO> toUserDTOList(List<User> users) {
        return users.stream().map(this::toUserDTO).toList();
    }

//    public User updateUser(Integer user_id, User updatedUser) {
//        var old_user = findUserById(user_id);
//
//        return userRepo.save
//    }
}
