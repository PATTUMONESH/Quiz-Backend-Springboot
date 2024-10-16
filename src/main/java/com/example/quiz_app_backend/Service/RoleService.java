package com.example.quiz_app_backend.Service;

import com.example.quiz_app_backend.Entity.Role;

import java.util.List;

public interface RoleService {

    Role saveRole(Role role);

    List<Role> getAllRoles();

}
