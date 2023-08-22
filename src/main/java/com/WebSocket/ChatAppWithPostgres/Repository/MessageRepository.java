package com.WebSocket.ChatAppWithPostgres.Repository;

import com.WebSocket.ChatAppWithPostgres.Exceptions.NotFoundException;
import com.WebSocket.ChatAppWithPostgres.Model.Message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("""
    Select m from Message m where m.receiver.id = :senderId or m.sender.id = :senderId
    """)
    List<Message> findAllByUserId(Integer senderId);
}
