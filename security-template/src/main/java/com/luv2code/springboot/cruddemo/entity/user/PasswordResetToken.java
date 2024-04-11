package com.luv2code.springboot.cruddemo.entity.user;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "email")
    private String email;

    @Column(name = "expiry_date")
    private Date expiryDate;

    public PasswordResetToken(String email) {
        this.token = UUID.randomUUID().toString();
        this.email = email;
        expiryDate = new Date(new Date().getTime() + EXPIRATION);
    }

    public PasswordResetToken(String token, String email, String password, Date expiryDate) {
        this.token = token;
        this.email = email;
        this.expiryDate = expiryDate;
    }

    public PasswordResetToken() {
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

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isTokenExpired() {
        return new Date().after(this.getExpiryDate());
    }
}
