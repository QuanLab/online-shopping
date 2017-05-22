package com.quanpv.dto;

import com.quanpv.domain.UserRoles;

import java.util.Set;

public class UserDTO {

    private String username;
    private Set<UserRoles> userRoles;


    public UserDTO(String username, Set<UserRoles> userRoles) {
        this.username = username;
        this.userRoles = userRoles;
    }

    public UserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<UserRoles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRoles> userRoles) {
        this.userRoles = userRoles;
    }
}
