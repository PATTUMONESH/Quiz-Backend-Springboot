package com.example.quiz_app_backend.Service;

import com.example.quiz_app_backend.Dto.UserDetailsDto;

import com.example.quiz_app_backend.Entity.UserDetails;
import com.example.quiz_app_backend.Entity.UserScore;

import java.util.Optional;


public interface UserService {

    UserDetails fetchUserByEmail(String email);

    UserDetails fetchUserByEmailAndPassword(String email,String password);

    Optional<UserDetails> findAdminById(Long id);

    String getUserEmailById(Long userId);

    UserDetails saveUser(UserDetailsDto userDetailsDto);

    UserScore saveUserScore(UserScore userScore, Long userId);

    UserDetails findByUserId(Long userId);

    // void sendMailUser(String mail, MailStructure mailStructure);

    // UserDetails saveUser(UserDetails userDetails);








  // List<Role> getAllUserRoles();


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


