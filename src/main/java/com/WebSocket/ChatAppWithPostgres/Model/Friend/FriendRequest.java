package com.WebSocket.ChatAppWithPostgres.Model.Friend;


import com.WebSocket.ChatAppWithPostgres.Model.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class FriendRequest {
    @Id
    @GeneratedValue
    private Integer id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "req_sender_id")
    private User requestSender;

    @ManyToOne
    @JoinColumn(name = "req_receiver_id")
    @JsonIgnore
    private User requestReceiver;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //set created_at and updated_at to present time when create new req
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    //set updated_at to present time when update req
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
