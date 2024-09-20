package com.example.quiz_app_backend.Service;

import com.example.quiz_app_backend.Dto.UserDetailsDto;
import com.example.quiz_app_backend.Entity.Role;
import com.example.quiz_app_backend.Entity.UserDetails;
import com.example.quiz_app_backend.Entity.UserScore;

import java.util.List;
import java.util.Optional;


public interface UserService {

   // void sendMailUser(String mail, MailStructure mailStructure);

   // UserDetails saveUser(UserDetails userDetails);

    UserDetails fetchUserByEmail(String email);

    UserDetails fetchUserByEmailAndPassword(String email,String password);

    String getUserEmailById(Long userId);
    UserScore saveUserScore(UserScore userScore, Long userId);



   UserDetails findByUserId(Long userId);


   List<Role> getAllUserRoles();

    UserDetails saveUser(UserDetailsDto userDetailsDto);
}

//    UserDetails fetchUserEmailById();
//     String getUserNameById(Long userId);

// UserScore saveUserScore(UserScore userScore, Long userId);

//    List<UserScore> getAllUserScores();

//    QuestionsConfig createQuestion(QuestionDto questionDto);



//    QuestionDto updateQuestion(Integer id, QuestionDto questionDto);

//    Optional<QuestionsConfig> getQuestionByQid(Integer Id);

//    List<QuestionsConfig> getAllQuestions();

//    void deleteQuestion(Integer id);

//    QuestionsConfig addQuestionToSubject(long subjectId, QuestionsConfig question);

//    Subject saveSubject(Subject subject);



//    List<Subject> getAllSubjects();


