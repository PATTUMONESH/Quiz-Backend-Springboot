package com.example.quiz_app_backend.Controller;

import com.example.quiz_app_backend.Dto.Response;
import com.example.quiz_app_backend.Entity.Role;
import com.example.quiz_app_backend.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping()
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/addrole")
    public ResponseEntity<Response> saveRole(@RequestBody Role role){
        roleService.saveRole(role);
        Response response=new Response();
        response.setMessage("role added successfully");
        response.setStatus(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }


@GetMapping("/getroles")
    public ResponseEntity<List<Role>> getAllRoles(){
        List<Role> roleList=roleService.getAllRoles();
        return new ResponseEntity<>(roleList,HttpStatus.OK);
    }

}
