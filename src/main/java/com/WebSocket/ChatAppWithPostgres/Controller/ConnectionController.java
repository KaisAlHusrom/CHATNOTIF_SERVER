package com.WebSocket.ChatAppWithPostgres.Controller;

import com.WebSocket.ChatAppWithPostgres.Services.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/connection")
public class ConnectionController {
    @GetMapping("isConnected")
    public ApiResponse checkConnections() {
        try {
            return ApiResponse.builder()
                    .success(true)
                    .error(null)
                    .message("Connected Successfully")
                    .result(true)
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .success(false)
                    .error(e.getMessage())
                    .message("Connection Failed")
                    .result(false)
                    .build();
        }
    }
}
