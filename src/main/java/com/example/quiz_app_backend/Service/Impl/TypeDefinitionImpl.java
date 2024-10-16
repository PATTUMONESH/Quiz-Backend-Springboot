package com.example.quiz_app_backend.Service.Impl;

import com.example.quiz_app_backend.Entity.TypeDefinition;
import com.example.quiz_app_backend.Repository.TypeDefinitionRepository;
import com.example.quiz_app_backend.Service.TypeDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeDefinitionImpl implements TypeDefinitionService {

    @Autowired
    private TypeDefinitionRepository typeDefinitionRepository;


    @Override
    public TypeDefinition saveTypeDefinition(TypeDefinition typeDefinition) {
        return typeDefinitionRepository.save(typeDefinition);
    }

    @Override
    public List<TypeDefinition> getAllTypeDefinition() {
        List<TypeDefinition> type=typeDefinitionRepository.findAll();
        return type;

    }
}
