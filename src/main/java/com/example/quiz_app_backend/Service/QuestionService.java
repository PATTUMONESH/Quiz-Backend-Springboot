package com.example.quiz_app_backend.Service;

import com.example.quiz_app_backend.Dto.ImageResponse;
import com.example.quiz_app_backend.Dto.QuestionCreateDto;
import com.example.quiz_app_backend.Dto.QuestionResponseDto;
import com.example.quiz_app_backend.Dto.QuestionUpdateDto;
import com.example.quiz_app_backend.Entity.QuestionsConfig;
import com.example.quiz_app_backend.Entity.UserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface QuestionService {
    QuestionsConfig createQuestion(QuestionCreateDto questionCreateDto);
    ImageResponse imageUpload(MultipartFile image) throws IOException;
    QuestionsConfig updateQuestion(Integer id, QuestionUpdateDto questionUpdateDto);
    byte[] getImage(String imageName) throws IOException;


   // Page<QuestionResponseDto> getAllQuesForAdmin(Pageable pageable);

//    QuestionCreateDto updateQuestion(Integer id, QuestionCreateDto questionCreateDto);

    Optional<QuestionsConfig> getQuestionByQid(Integer Id);

    List<QuestionsConfig> getAllQuestions();

    void deleteQuestion(Integer id);

    List<QuestionResponseDto> getAllQuesForUser();



    Optional<UserDetails> findUserById(Long id);


    //List<QuestionResponseDto> getAllQuesForAdmin();


    Page<QuestionResponseDto> getAllQuesForAdmin(Pageable pageable);


}


//QuestionsConfig addQuestionToSubject(long subjectId, QuestionsConfig question);

// String checkingAnswer(Integer questionId,String selectedOption);

//String checkingAnswer(Map<Integer,String> requestData);

//    AnswerCheckResponse checkingAnswers(AnswerCheckRequest requestData);

// AnswerCheckResponse checkingAnswers(Long id,AnswerCheckRequest requestData);

//public AnswerCheckResponse checkingAnswers(Long userId,AnswerCheckRequest requestData,Long subjectId);



