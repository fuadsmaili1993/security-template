package com.luv2code.springboot.cruddemo.dao;

import com.luv2code.springboot.cruddemo.entity.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    public Optional<UserInfo> findByEmail(String email);
}
