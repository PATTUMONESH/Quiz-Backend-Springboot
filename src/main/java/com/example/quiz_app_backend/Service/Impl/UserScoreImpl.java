package com.example.quiz_app_backend.Service.Impl;

import com.example.quiz_app_backend.Dto.AnswerCheckRequest;
import com.example.quiz_app_backend.Dto.AnswerCheckResponse;
import com.example.quiz_app_backend.Dto.UserScoreDto;
import com.example.quiz_app_backend.Entity.QuestionsConfig;
import com.example.quiz_app_backend.Entity.Subject;
import com.example.quiz_app_backend.Entity.UserDetails;
import com.example.quiz_app_backend.Entity.UserScore;
import com.example.quiz_app_backend.Exception.ResourceNotFoundException;
import com.example.quiz_app_backend.Repository.QuestionListRepository;
import com.example.quiz_app_backend.Repository.SubjectRepository;
import com.example.quiz_app_backend.Repository.UserRepository;
import com.example.quiz_app_backend.Repository.UserScoreRepository;
import com.example.quiz_app_backend.Service.UserScoreService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserScoreImpl implements UserScoreService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserScoreRepository userScoreRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private QuestionListRepository questionListRepository;
    @Autowired
    private Configuration freemarkerConfig;
    @Autowired
    private JavaMailSender mailSender;


    @Override
    public AnswerCheckResponse checkingAnswers(Long userId,AnswerCheckRequest requestData,Long subjectId) throws MessagingException, TemplateException, IOException {

        UserDetails userDetails=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user id not found:"+userId));
        Subject subject=subjectRepository.findById(subjectId).orElseThrow(()->new ResourceNotFoundException("subject id not found:"+subjectId));
       List<QuestionsConfig> selectedRandomQues =questionListRepository.findRandomQuestions();

        Map<Integer, String> results = new HashMap<>();
        UserScore userScore=new UserScore();
        userScore.setScore(0);
        userScore.setCorrect(0);
        userScore.setInCorrect(0);
        userScore.setNotVisited(0);
        userScore.setScoreTimeStamp(LocalDateTime.now());
//        userScore.setTotal(0);
        userScore.setTotal(selectedRandomQues.size());
        userScore.setVisitedQues(0);
        userScore.setUserDetails(userDetails);
        userScore.setFirstName(userDetails.getFirstName());
        userScore.setLastName(userDetails.getLastName());
        userScore.setSubject(subject);

        //  UserScore userScore=userScoreRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("user not found with this provided id:"+id));
        for (Map.Entry<Integer, String> entry : requestData.getSelectedOption().entrySet()) {
            Integer questionId = entry.getKey();
            String selectedOption = entry.getValue();

             //userScore.setTotal(userScore.getTotal()+1);
            // Fetch the question using the provided questionId
            QuestionsConfig questionsConfig = questionListRepository.findById(questionId)
                    .orElseThrow(() -> new ResourceNotFoundException("question is not found with this id: " + questionId));
            // Get the correct answer for the question
            String answer = questionsConfig.getAnswer();

          // userScore.setTotal(userScore.getTotal() + 1);
            // Compare the selected option with the correct answer

            if (selectedOption == null || selectedOption.isEmpty()) {
                userScore.setNotVisited(userScore.getNotVisited() + 1);
                results.put(questionId, "not visited");
            } else if (selectedOption.equals(answer)) {
                userScore.setCorrect(userScore.getCorrect() + 1);
                userScore.setScore(userScore.getScore() + 10);
                userScore.setVisitedQues(userScore.getVisitedQues() +1);
                results.put(questionId, "answer is correct");
            } else {
                userScore.setInCorrect(userScore.getInCorrect() + 1);
                userScore.setScore(userScore.getScore() - 5);
                userScore.setVisitedQues(userScore.getVisitedQues() +1);
                results.put(questionId, "answer is incorrect");
            }
        }
        int unattemptedQuestions=selectedRandomQues.size()-userScore.getVisitedQues();
        userScore.setNotVisited(unattemptedQuestions);

        userScoreRepository.save(userScore);
        // Create a response object
        AnswerCheckResponse response = new AnswerCheckResponse();
        response.setResult(results);
        response.setScore(userScore.getScore());
        response.setTotal(userScore.getTotal());
        response.setCorrect(userScore.getCorrect());
        response.setInCorrect(userScore.getInCorrect());
        response.setNotVisited(userScore.getNotVisited());
        response.setVisitedQues(userScore.getVisitedQues());

        //send mail
        sendQuizResultsEmail(userDetails, userScore);
        return response;
    }

    @Override
    public void sendQuizResultsEmail(UserDetails userDetails, UserScore userScore) throws MessagingException, IOException, TemplateException {
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

        Map<String, Object> model = new HashMap<>();
        model.put("name", userDetails.getFirstName() + " " + userDetails.getLastName());
        model.put("score", userScore.getScore());
        model.put("correctAnswer", userScore.getCorrect());
        model.put("wrongAnswer", userScore.getInCorrect());
        model.put("unAttemptedQuestion", userScore.getNotVisited());
        model.put("AttemptedQuestions", userScore.getVisitedQues());
        model.put("totalQuestions", userScore.getTotal());

        Template template = freemarkerConfig.getTemplate("scoreEmailTemplate.ftl");
        StringWriter stringWriter = new StringWriter();
        template.process(model, stringWriter);
        String emailContent = stringWriter.getBuffer().toString();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(userDetails.getEmail());
        helper.setSubject("Your Quiz Results");
        helper.setText(emailContent, true);

        mailSender.send(message);
    }

    @Override
    public List<UserScore> getAllUserScores() {

        return userScoreRepository.findAllScoresByDesc();
//        return userScoreRepository.findAll();
    }


//@Override
//public Map<String, List<UserScore>> getAllUserScoresGroupedBySubject() {
//    List<UserScore> scores = userScoreRepository.findAllScoresGroupedBySubject();
//
//    // Group by subject name using streams
//    return scores.stream()
//            .collect(Collectors.groupingBy(score -> score.getSubject().getSubject()));
//}




    @Override
    public void deleteUserScoreById(Long id) {
        UserScore deleteScore=userScoreRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("user score is not found with this provided id"+id));
        userScoreRepository.delete(deleteScore);

    }

    @Override
    public List<UserScore> findByFirstNameOrLastName(String name) {
        return userScoreRepository.findByFirstNameOrLastName(name);
    }

//    @Override
//    public UserScore searchUser(String name) {
//
//        return userScoreRepository.searchByName(name);
//    }


}




//@Override
//public UserScore saveUserScore(UserScore userScore, Long userId) {
//    UserDetails userDetails = userRepository.findById(userId)
//            .orElseThrow(() -> new RuntimeException("User not found ::" + userId));
//    userScore.setFirstName(userDetails.getFirstName());
//    userScore.setLastName(userDetails.getLastName());
//    userScore.setUserDetails(userDetails);
//    return userScoreRepository.save(userScore);
//}



//@Override
//public List<UserScore> getAllUserScores() {
//    return userScoreRepository.findAll();
//}
