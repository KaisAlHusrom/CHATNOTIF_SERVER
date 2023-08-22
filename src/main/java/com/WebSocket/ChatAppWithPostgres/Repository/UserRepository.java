package com.WebSocket.ChatAppWithPostgres.Repository;

import com.WebSocket.ChatAppWithPostgres.Model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByUserName(String userName);

    @Query("""
    Select u from User u where u.userName LIKE %?1%
    """)
    public List<User> findAllByUserName(String userName);
}
