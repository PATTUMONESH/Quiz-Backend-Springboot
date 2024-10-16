package com.example.quiz_app_backend.Service;

import com.example.quiz_app_backend.Entity.TypeDefinition;

import java.util.List;

public interface TypeDefinitionService {


    TypeDefinition saveTypeDefinition(TypeDefinition typeDefinition);

    List<TypeDefinition> getAllTypeDefinition();

}
