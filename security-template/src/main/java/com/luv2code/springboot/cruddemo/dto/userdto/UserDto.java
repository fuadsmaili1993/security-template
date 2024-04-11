package com.luv2code.springboot.cruddemo.dto.userdto;

import com.luv2code.springboot.cruddemo.entity.user.UserInfo;

import java.util.Collections;
import java.util.List;

public class UserDto {

    private int id;

    private String username;

    private String email;

    private String password;

    private List<String> roles;

    public UserDto() {
    }

    public UserDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserDto(UserInfo userInfo) {
        this.id = userInfo.getId();
        this.username = userInfo.getUsername();
        this.email = userInfo.getEmail();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles != null ? roles: Collections.emptyList();
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
