package com.WebSocket.ChatAppWithPostgres.Repository;

import com.WebSocket.ChatAppWithPostgres.Model.Friend.FriendRequest;
import com.WebSocket.ChatAppWithPostgres.Model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {

    @Query("""
    Select r from FriendRequest r where r.requestReceiver.id = :id
    """)
    public List<FriendRequest> findAllByRequestReceiverId(Integer id);

    @Query("""
    select r from FriendRequest r where r.requestReceiver.id = :receiverId and r.requestSender.id = :senderId
    """)
    public FriendRequest findSpecificRequest(Integer senderId, Integer receiverId);
}
