package com.WebSocket.ChatAppWithPostgres.Config;


import com.WebSocket.ChatAppWithPostgres.Model.User.ConnectStatus;
import com.WebSocket.ChatAppWithPostgres.Repository.TokenRepository;
import com.WebSocket.ChatAppWithPostgres.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
    {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);

        var storedToken = tokenRepository.findByToken(jwt).orElse(null);

        if(storedToken != null) {
            storedToken.setRevoked(true);
            storedToken.setExpired(true);
            tokenRepository.save(storedToken);
        }

        var user = userRepository.findByToken(jwt);
        if (user != null) {
            user.setConnectStatus(ConnectStatus.UNCONNECTED);
            userRepository.save(user);
        }
    }
}
