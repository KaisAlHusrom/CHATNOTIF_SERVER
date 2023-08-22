package com.WebSocket.ChatAppWithPostgres.Model.User;

import com.WebSocket.ChatAppWithPostgres.Model.Friend.FriendRequest;
import com.WebSocket.ChatAppWithPostgres.Model.Message.Message;
import com.WebSocket.ChatAppWithPostgres.Model.Token.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "user_name", unique = true)
    private String userName;

    private String email;

    @JsonIgnore
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //set created_at and updated_at to present time when create new user
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    //set updated_at to present time when update user
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    //Sent Messages
    @OneToMany(mappedBy = "sender")
    @JsonIgnore
    private List<Message> sentMessages = new ArrayList<>();

    //Received Messages
    @OneToMany(mappedBy = "receiver")
    @JsonIgnore
    private List<Message> receivedMessages = new ArrayList<>();

    //Sent Friend Requests
    @OneToMany(mappedBy = "requestSender")
    @JsonIgnore
    private List<FriendRequest> sentRequests = new ArrayList<>();

    //Received Friend Requests
    @OneToMany(mappedBy = "requestReceiver")
    @JsonIgnore
    private List<FriendRequest> receivedRequests = new ArrayList<>();


    @ManyToMany(mappedBy = "friendWith", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> friendFrom = new ArrayList<>();


    @ManyToMany( cascade = CascadeType.ALL)
    @JoinTable(name = "user_friends",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "friend_with_id")})
    @JsonIgnore
    private List<User> friendWith = new ArrayList<>();

//    public UserDTO toDto() {
//        return UserDTO.builder()
//                .userName(this.userName)
//                .email(this.email)
//                .createdAt(this.createdAt)
//                .updatedAt(this.updatedAt)
//                .role(this.role)
//                .
//                .build();
//    }

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Token> tokens;

    @Enumerated(EnumType.STRING)
    private Role role;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
