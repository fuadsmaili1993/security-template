package com.luv2code.springboot.cruddemo.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthorizationHelper {

    public static boolean isAdminOrCurrentUser(String email){
        boolean isAdmin=SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        boolean isCurrentUser=SecurityContextHolder.getContext().getAuthentication().getName().equals(email);
        return isAdmin || isCurrentUser;
    }
}
