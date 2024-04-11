package com.luv2code.springboot.cruddemo.dao;

import com.luv2code.springboot.cruddemo.entity.user.PasswordResetToken;
import com.luv2code.springboot.cruddemo.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {

    public Optional<PasswordResetToken> findByTokenAndEmail(String token,String email);
}
