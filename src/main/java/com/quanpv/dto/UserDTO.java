package com.quanpv.dto;

import com.quanpv.domain.Role;
import java.util.Set;

public class UserDTO {

    private String username;
    private Set<Role> roles;


    public UserDTO(String username, Set<Role> roles) {
        this.username = username;
        this.roles = roles;
    }

    public UserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
