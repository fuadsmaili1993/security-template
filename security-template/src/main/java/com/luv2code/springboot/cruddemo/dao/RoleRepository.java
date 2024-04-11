package com.luv2code.springboot.cruddemo.dao;

import com.luv2code.springboot.cruddemo.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    public List<Role> findByEmail(final String email);

    public long deleteByEmail(String email);
}
