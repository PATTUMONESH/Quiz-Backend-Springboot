package com.example.quiz_app_backend.Service;

import com.example.quiz_app_backend.Dto.AnswerCheckRequest;
import com.example.quiz_app_backend.Dto.AnswerCheckResponse;
import com.example.quiz_app_backend.Entity.UserDetails;
import com.example.quiz_app_backend.Entity.UserScore;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;


public interface UserScoreService {
    AnswerCheckResponse checkingAnswers(Long userId, AnswerCheckRequest requestData, Long subjectId) throws MessagingException, TemplateException, IOException;
    void sendQuizResultsEmail(UserDetails userDetails, UserScore userScore) throws MessagingException, IOException, TemplateException;
    List<UserScore> getAllUserScores();

    void deleteUserScoreById(Long id);


    UserScore searchUser(String name);
}


//    public UserScore saveUserScore(UserScore userScore, Long userId);
//List<UserScore> getAllUserScores();
