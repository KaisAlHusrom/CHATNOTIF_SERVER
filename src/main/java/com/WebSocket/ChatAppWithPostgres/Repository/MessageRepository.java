package com.WebSocket.ChatAppWithPostgres.Repository;

import com.WebSocket.ChatAppWithPostgres.Exceptions.NotFoundException;
import com.WebSocket.ChatAppWithPostgres.Model.Message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("""
    Select m from Message m where\s
        (m.receiver.id = :receiverId and m.sender.id = :senderId)
        or\s
        (m.receiver.id = :senderId and m.sender.id = :receiverId)

    """)
    List<Message> findAllByUserId(Integer senderId, Integer receiverId);
}
