package com.example.quiz_app_backend.Controller;

import com.example.quiz_app_backend.Dto.Response;
import com.example.quiz_app_backend.Entity.Subject;
import com.example.quiz_app_backend.Service.QuestionService;
import com.example.quiz_app_backend.Service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins ="*",allowedHeaders = "*")
@RequestMapping()
public class SubjectController {

   @Autowired
    private SubjectService subjectService;

    @PostMapping("/addSubject")
    public ResponseEntity<Response> addSubject(@RequestBody Subject subject){
        subjectService.saveSubject(subject);
        Response response = new Response();
        response.setMessage("Question set added successfully");
        response.setStatus(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllSubjects")
    public ResponseEntity<List<Subject>> getSubjects(){
        List<Subject> subjectList=subjectService.getAllSubjects();
        return new ResponseEntity<>(subjectList,HttpStatus.OK);
    }


}
