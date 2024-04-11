package com.luv2code.springboot.cruddemo.security;

import com.luv2code.springboot.cruddemo.entity.user.Role;
import com.luv2code.springboot.cruddemo.entity.user.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoUserDetails implements UserDetails {

    private String email;
    private String password;
    private List<GrantedAuthority> authorities;

    boolean enabled;

    public UserInfoUserDetails(UserInfo userInfo, List<Role> roles) {
        email = userInfo.getEmail();
        password = userInfo.getPassword();
        authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
        enabled = userInfo.getEnabled() == 1;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
