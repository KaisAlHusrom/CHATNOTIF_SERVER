package com.WebSocket.ChatAppWithPostgres.Controller;

import com.WebSocket.ChatAppWithPostgres.Model.User.User;
import com.WebSocket.ChatAppWithPostgres.Model.User.UserDTO;
import com.WebSocket.ChatAppWithPostgres.Repository.UserRepository;
import com.WebSocket.ChatAppWithPostgres.Services.ApiResponse;
import com.WebSocket.ChatAppWithPostgres.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get-all-users")
    public ResponseEntity<List<UserDTO>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/get-user/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Integer userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }


    @PostMapping("/get-all-with-user-name")
    public ResponseEntity<ApiResponse> findAllByUserName(@RequestBody User user) {
        return ResponseEntity.ok(userService.findAllByUserName(user.getUsername()));
    }

    @PutMapping("/add_friend/{user_id}/{friend_with}")
    public ResponseEntity<ApiResponse> addFriend(
            @PathVariable Integer user_id,
            @PathVariable Integer friend_with) {
        return ResponseEntity.ok(userService.addFriend(user_id, friend_with));
    }

    @PutMapping("/remove_friend/{user_id}/{friend_with}")
    public ResponseEntity<ApiResponse> removeFriend(
            @PathVariable Integer user_id,
            @PathVariable Integer friend_with) {
        return ResponseEntity.ok(userService.removeFriend(user_id, friend_with));
    }

    @GetMapping("/{user_id}/get-friends")
    public ResponseEntity<ApiResponse> getFriends(@PathVariable Integer user_id) {
        return ResponseEntity.ok(userService.getFriends(user_id));
    }
}
