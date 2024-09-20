package com.example.quiz_app_backend.Service;

import com.example.quiz_app_backend.Dto.QuestionCreateDto;
import com.example.quiz_app_backend.Dto.QuestionResponseDto;
import com.example.quiz_app_backend.Dto.QuestionUpdateDto;
import com.example.quiz_app_backend.Entity.QuestionsConfig;
import java.util.List;
import java.util.Optional;

public interface QuestionService {
    QuestionsConfig createQuestion(QuestionCreateDto questionCreateDto);
    public QuestionsConfig updateQuestion(Integer id, QuestionUpdateDto questionUpdateDto);

//    QuestionCreateDto updateQuestion(Integer id, QuestionCreateDto questionCreateDto);

    Optional<QuestionsConfig> getQuestionByQid(Integer Id);

    List<QuestionsConfig> getAllQuestions();

    void deleteQuestion(Integer id);

    List<QuestionResponseDto> getAllQuesForUser();

    List<QuestionResponseDto> getAllQuesForAdmin();

}


//QuestionsConfig addQuestionToSubject(long subjectId, QuestionsConfig question);

// String checkingAnswer(Integer questionId,String selectedOption);

//String checkingAnswer(Map<Integer,String> requestData);

//    AnswerCheckResponse checkingAnswers(AnswerCheckRequest requestData);

// AnswerCheckResponse checkingAnswers(Long id,AnswerCheckRequest requestData);

//public AnswerCheckResponse checkingAnswers(Long userId,AnswerCheckRequest requestData,Long subjectId);

//    Optional<UserDetails> findUserById(Long id);

