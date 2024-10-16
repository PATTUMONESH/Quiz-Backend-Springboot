package com.example.quiz_app_backend.Controller;

import com.example.quiz_app_backend.Dto.Response;
import com.example.quiz_app_backend.Entity.TypeDefinition;
import com.example.quiz_app_backend.Service.TypeDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping
public class TypeDefinitionController {

    @Autowired
    private TypeDefinitionService typeDefinitionService;


    @PostMapping("/addType")
    public ResponseEntity<Response> saveTypeDefinition(@RequestBody TypeDefinition typeDefinition){
        typeDefinitionService.saveTypeDefinition(typeDefinition);
        Response response=new Response();
        response.setMessage("Type added successfully");
        response.setStatus(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("/getAllTypes")
    public ResponseEntity<List<TypeDefinition>> getAllTypes(){
        List<TypeDefinition> typeList=typeDefinitionService.getAllTypeDefinition();
        return new ResponseEntity<>(typeList,HttpStatus.OK);
    }

}
