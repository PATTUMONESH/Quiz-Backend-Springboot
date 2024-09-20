package com.example.quiz_app_backend.Service;

import com.example.quiz_app_backend.Entity.Subject;
import java.util.List;

public interface SubjectService {

    Subject saveSubject(Subject subject);

    List<Subject> getAllSubjects();

    Subject findBySubjectId(Long subId);


}
