package com.luv2code.springboot.cruddemo.rest;


import com.luv2code.springboot.cruddemo.dto.userdto.ChangePasswordDto;
import com.luv2code.springboot.cruddemo.dto.userdto.LoginDto;
import com.luv2code.springboot.cruddemo.dto.userdto.PasswordResetTokenDto;
import com.luv2code.springboot.cruddemo.dto.userdto.UserDto;
import com.luv2code.springboot.cruddemo.entity.user.Role;
import com.luv2code.springboot.cruddemo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/add")
    public ResponseEntity<String> addNewUser(@RequestBody UserDto userDto) {
        return userInfoService.addUser(userDto);
    }

    @PostMapping("/changepassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        return userInfoService.changePassword(changePasswordDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        return userInfoService.login(loginDto);
    }

    @GetMapping("/resetpassword")
    public ResponseEntity<String> resetPassword(@RequestParam String email) throws UnknownHostException {
        return userInfoService.resetPassword(email);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return userInfoService.getAllUsers();
    }

    @GetMapping("/get")
    public ResponseEntity<UserDto> getUserWithRoles(@RequestParam String email) {
        return userInfoService.findUserWithRolesByEmail(email);
    }

    @PostMapping("/resetbytoken")
    public ResponseEntity<String> resetPasswordWithToken(@RequestBody PasswordResetTokenDto passwordResetTokenDto) {
        return userInfoService.processResetPassword(passwordResetTokenDto);
    }

    @PostMapping("/addRole")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addRole(@RequestBody Role role) {
        return userInfoService.addRole(role);
    }

    @PostMapping("/addRoles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addRoles(@RequestBody List<Role> roles) {
        return userInfoService.addRoles(roles);
    }

    @PostMapping("/deleterole")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteRole(@RequestParam String email) {
        return userInfoService.deleteRolesByEmail(email);
    }

    @PostMapping("/enable")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String enable(@RequestParam String email) {
        return userInfoService.enableUser(email);
    }

    @PostMapping("/disable")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String disable(@RequestParam String email) {
        return userInfoService.disableUser(email);
    }
}
