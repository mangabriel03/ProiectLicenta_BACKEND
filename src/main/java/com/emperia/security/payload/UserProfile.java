package com.emperia.security.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
public class UserProfile {
    private Long id;
    private String username;
    private String name;
    private Instant joinedAt;

    public UserProfile(Long id, String username, String name, Instant joinedAt) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.joinedAt = joinedAt;
    }

}
