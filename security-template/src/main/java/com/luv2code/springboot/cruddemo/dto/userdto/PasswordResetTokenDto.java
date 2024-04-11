package com.luv2code.springboot.cruddemo.dto.userdto;


public class PasswordResetTokenDto {

    private String token;

    private String email;

    private String password;

    public PasswordResetTokenDto(String token, String email, String password) {
        this.token = token;
        this.email = email;
        this.password = password;
    }

    public PasswordResetTokenDto() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
}
