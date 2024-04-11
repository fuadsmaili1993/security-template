package com.luv2code.springboot.cruddemo.service;

import com.luv2code.springboot.cruddemo.dao.RoleRepository;
import com.luv2code.springboot.cruddemo.dao.UserInfoRepository;
import com.luv2code.springboot.cruddemo.entity.user.Role;
import com.luv2code.springboot.cruddemo.entity.user.UserInfo;
import com.luv2code.springboot.cruddemo.security.UserInfoUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userInfoRepository.findByEmail(email);
        if (!userInfo.isPresent()) {
            throw new RuntimeException("User could not be found");
        }
        List<Role> roles = roleRepository.findByEmail(email);
        if (roles.isEmpty()) {
            throw new RuntimeException("User has no roles");
        }
        return new UserInfoUserDetails(userInfo.get(), roles);
    }
}
