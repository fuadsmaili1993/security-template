package com.luv2code.springboot.cruddemo.service;


import com.luv2code.springboot.cruddemo.dao.PasswordResetTokenRepository;
import com.luv2code.springboot.cruddemo.dao.RoleRepository;
import com.luv2code.springboot.cruddemo.dao.UserInfoRepository;
import com.luv2code.springboot.cruddemo.dto.userdto.ChangePasswordDto;
import com.luv2code.springboot.cruddemo.dto.userdto.LoginDto;
import com.luv2code.springboot.cruddemo.dto.userdto.PasswordResetTokenDto;
import com.luv2code.springboot.cruddemo.dto.userdto.UserDto;
import com.luv2code.springboot.cruddemo.entity.user.PasswordResetToken;
import com.luv2code.springboot.cruddemo.entity.user.Role;
import com.luv2code.springboot.cruddemo.entity.user.UserInfo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserInfoService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserInfo> userInDb = userInfoRepository.findAll();
        if (userInDb.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST);
        }
        List<Role> roles = roleRepository.findAll();
        List<UserDto> usersList=new ArrayList<>(userInDb.size());
        for (UserInfo userInfo:userInDb) {
            UserDto userDto=new UserDto(userInfo);
            userDto.setRoles(roles.stream()
                    .filter(role -> role.getEmail().equals(userDto.getEmail()))
                    .map(role -> role.getRole()).collect(Collectors.toList()));
            usersList.add(userDto);
        }
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    public ResponseEntity<UserDto> findUserWithRolesByEmail(String email) {
       if(!AuthorizationHelper.isAdminOrCurrentUser(email)){
            return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        }
        Optional<UserInfo> userInfo = userInfoRepository.findByEmail(email);
        if (!userInfo.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        UserDto userDto=new UserDto(userInfo.get());
        List<Role> roles = roleRepository.findByEmail(email);
        userDto.setRoles(roles.stream().map(role -> role.getRole()).collect(Collectors.toList()));
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    public ResponseEntity<String> addUser(UserDto userDto) {
        Optional<UserInfo> userInDb = userInfoRepository.findByEmail(userDto.getEmail());
        if (userInDb.isPresent()) {
            return new ResponseEntity<>("User already exists with that email", HttpStatus.BAD_REQUEST);
        }
        UserInfo newUser = new UserInfo(userDto.getUsername(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()));
        UserInfo savedUser = userInfoRepository.save(newUser);
        return new ResponseEntity<>("User added successfully", HttpStatus.OK);
    }

    public ResponseEntity<String> changePassword(ChangePasswordDto changePasswordDto) {
        Optional<UserInfo> userInDb = userInfoRepository.findByEmail(changePasswordDto.getEmail());
        if (!userInDb.isPresent()) {
            return new ResponseEntity<>("User could not be found", HttpStatus.BAD_REQUEST);
        }
        String existingPasswordInDb = userInDb.get().getPassword();
        if (!this.passwordEncoder.matches(changePasswordDto.getExistingPassword(), existingPasswordInDb)) {
            return new ResponseEntity<>("Old password was wrong", HttpStatus.BAD_REQUEST);
        }

        userInDb.get().setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userInfoRepository.save(userInDb.get());
        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    }

    public ResponseEntity<String> login(LoginDto loginDto) {
        Optional<UserInfo> userInDb = userInfoRepository.findByEmail(loginDto.getEmail());
        if (!userInDb.isPresent()) {
            return new ResponseEntity<>("User could not be found", HttpStatus.BAD_REQUEST);
        }
        String existingPasswordInDb = userInDb.get().getPassword();
        if (!this.passwordEncoder.matches(loginDto.getPassword(), existingPasswordInDb)) {
            return new ResponseEntity<>("email or password was wrong", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully authenticated", HttpStatus.OK);
    }

    public ResponseEntity<String> resetPassword(String email) throws UnknownHostException {
        Optional<UserInfo> userInDb = userInfoRepository.findByEmail(email);
        if (!userInDb.isPresent()) {
            return new ResponseEntity<>("User could not be found", HttpStatus.BAD_REQUEST);
        }
        PasswordResetToken passwordResetToken = new PasswordResetToken(email);
        this.passwordResetTokenRepository.save(passwordResetToken);
        emailService.sendEmail(passwordResetToken);
        return new ResponseEntity<>("Reset password sent successfully", HttpStatus.OK);
    }

    public ResponseEntity<String> processResetPassword(PasswordResetTokenDto passwordResetTokenDto) {
        Optional<PasswordResetToken> passwordResetTokenDb = passwordResetTokenRepository.findByTokenAndEmail(passwordResetTokenDto.getToken(), passwordResetTokenDto.getEmail());
        if (!passwordResetTokenDb.isPresent()) {
            return new ResponseEntity<>("Token could not be found", HttpStatus.BAD_REQUEST);
        }
        PasswordResetToken passwordResetTokenTT = passwordResetTokenDb.get();
        if (passwordResetTokenTT.isTokenExpired()) {
            return new ResponseEntity<>("Token already expired", HttpStatus.BAD_REQUEST);
        }
        Optional<UserInfo> userInDb = userInfoRepository.findByEmail(passwordResetTokenTT.getEmail());
        if (!userInDb.isPresent()) {
            return new ResponseEntity<>("User could not be found", HttpStatus.BAD_REQUEST);
        }
        userInDb.get().setPassword(passwordEncoder.encode(passwordResetTokenDto.getPassword()));
        this.userInfoRepository.save(userInDb.get());
        this.passwordResetTokenRepository.delete(passwordResetTokenTT);
        return new ResponseEntity<>("Password has been changed successfully", HttpStatus.OK);
    }

    public String addRole(Role role) {
        roleRepository.save(role);
        return "role added to the system";
    }

    public String addRoles(List<Role> roles) {
        for (Role role : roles) {
            roleRepository.save(role);
        }
        return "roles added to the system";
    }

    public String enableUser(String email) {
        Optional<UserInfo> userInDb = userInfoRepository.findByEmail(email);
        if (!userInDb.isPresent()) {
            throw new RuntimeException("User does not exist");
        }
        userInDb.get().setEnabled(1);
        userInfoRepository.save(userInDb.get());
        return "user has been enabled";
    }

    public String disableUser(String email) {
        Optional<UserInfo> userInDb = userInfoRepository.findByEmail(email);
        if (!userInDb.isPresent()) {
            throw new RuntimeException("User does not exist");
        }
        userInDb.get().setEnabled(0);
        userInfoRepository.save(userInDb.get());
        return "user has been disabled";
    }

    public String deleteRolesByEmail(String email) {
        roleRepository.deleteByEmail(email);
        return "all roles deleted by given email";
    }
}
