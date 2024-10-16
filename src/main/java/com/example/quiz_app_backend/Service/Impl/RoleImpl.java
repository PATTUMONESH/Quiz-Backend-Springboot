package com.example.quiz_app_backend.Service.Impl;

import com.example.quiz_app_backend.Entity.Role;
import com.example.quiz_app_backend.Repository.RoleRepository;
import com.example.quiz_app_backend.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> role=roleRepository.findAll();
        return role;
    }


}
