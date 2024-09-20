package com.example.quiz_app_backend.Service.Impl;

import com.example.quiz_app_backend.Entity.Subject;
import com.example.quiz_app_backend.Exception.ResourceNotFoundException;
import com.example.quiz_app_backend.Repository.SubjectRepository;
import com.example.quiz_app_backend.Service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SubjectImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public List<Subject> getAllSubjects() {
        List<Subject> sub = subjectRepository.findAll();
        return sub;
    }

    @Override
    public Subject findBySubjectId(Long subId) {
        return subjectRepository.findById(subId).orElseThrow(()->new ResourceNotFoundException("subject is not found with this"+subId));
    }


}
