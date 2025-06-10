package com.emperia.security.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSummary {
    private Long id;
    private String username;
    private String name;
    private boolean isAdmin;

    public UserSummary(Long id, String username, String name, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.isAdmin = isAdmin;
    }

}
