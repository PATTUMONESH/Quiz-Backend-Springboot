package com.example.quiz_app_backend.Service.Impl;

import com.example.quiz_app_backend.Dto.QuestionCreateDto;
import com.example.quiz_app_backend.Dto.QuestionResponseDto;
import com.example.quiz_app_backend.Dto.QuestionUpdateDto;
import com.example.quiz_app_backend.Entity.QuestionsConfig;
import com.example.quiz_app_backend.Entity.Subject;
import com.example.quiz_app_backend.Exception.ResourceNotFoundException;
import com.example.quiz_app_backend.Repository.QuestionListRepository;
import com.example.quiz_app_backend.Repository.SubjectRepository;
import com.example.quiz_app_backend.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionImpl implements QuestionService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private QuestionListRepository questionListRepository;

    @Override
    public QuestionsConfig createQuestion(QuestionCreateDto questionCreateDto) {
        Subject subjectId = subjectRepository.findById(questionCreateDto.getSubjectId()).orElseThrow(() -> new ResourceNotFoundException("subject not found with this id"));
        QuestionsConfig questionsConfig = new QuestionsConfig();
        questionsConfig.setQuestion(questionCreateDto.getQuestion());
        questionsConfig.setAnswer(questionCreateDto.getAnswer());
        questionsConfig.setOption1(questionCreateDto.getOption1());
        questionsConfig.setOption2(questionCreateDto.getOption2());
        questionsConfig.setOption3(questionCreateDto.getOption3());
        questionsConfig.setOption4(questionCreateDto.getOption4());
        questionsConfig.setSubject(subjectId);
        return questionListRepository.save(questionsConfig);
    }

    @Override
    public QuestionsConfig updateQuestion(Integer id,QuestionUpdateDto questionUpdateDto) {
        QuestionsConfig questionsConfig = questionListRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question not found for this id :: " + id));
        Subject subject = subjectRepository.findById(questionsConfig.getSubject().getId()).orElseThrow(() -> new ResourceNotFoundException("subject not found with this id"));
        questionsConfig.setQuestion(questionUpdateDto.getQuestion());
        questionsConfig.setOption1(questionUpdateDto.getOption1());
        questionsConfig.setOption2(questionUpdateDto.getOption2());
        questionsConfig.setOption3(questionUpdateDto.getOption3());
        questionsConfig.setOption4(questionUpdateDto.getOption4());
        questionsConfig.setAnswer(questionUpdateDto.getAnswer());
        questionsConfig.setSubject(subject);
        final QuestionsConfig updatedQuestion = questionListRepository.save(questionsConfig);
        return questionsConfig;
    }

    @Override
    public Optional<QuestionsConfig> getQuestionByQid(Integer Id) {
        return questionListRepository.findById(Id);
    }

    @Override
    public List<QuestionsConfig> getAllQuestions() {
        return questionListRepository.findAll();
    }

    @Override
    public void deleteQuestion(Integer id) {
        QuestionsConfig deletedQues = questionListRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question not found for this id :: "+id));
        questionListRepository.delete(deletedQues);
    }




    @Override
    public List<QuestionResponseDto> getAllQuesForUser() {

        List<QuestionsConfig> questions = questionListRepository.findRandomQuestions();

        for(QuestionsConfig que:questions){
            System.out.println(que.toString());
        }


        Collections.shuffle(questions);
System.out.println("=================================");
        for(QuestionsConfig que:questions){
            System.out.println(que.toString());
        }

        return questions.stream()
                .map(question -> {

                    List<String> options = new ArrayList<>(List.of(
                            question.getOption1(),
                            question.getOption2(),
                            question.getOption3(),
                            question.getOption4()
                    ));

                    System.out.println("=================================");
                    for(String op:options){
                        System.out.println(op);
                    }

                    Collections.shuffle(options);

                    System.out.println("=================5===============");
                    for(String op:options){
                        System.out.println(op);
                    }


                    return new QuestionResponseDto(
                            question.getQuesid(),
                            question.getQuestion(),
                            options.get(0),
                            options.get(1),
                            options.get(2),
                            options.get(3),
                            question.getAnswer(),
                            question.getSubject().getId()
                    );
                })
                .collect(Collectors.toList());
    }




    @Override
    public List<QuestionResponseDto> getAllQuesForAdmin(){
        List<QuestionsConfig> obj=questionListRepository.findAll();
        for(QuestionsConfig ob:obj){
            System.out.println(ob);
        }
        return obj.stream()
                .map(question -> new QuestionResponseDto(
                        question.getQuesid(),
                        question.getQuestion(),
                        question.getOption1(),
                        question.getOption2(),
                        question.getOption3(),
                        question.getOption4(),
                        question.getAnswer(),
                        question.getSubject().getId()  // Get the ID of the associated subject
                ))
                .collect(Collectors.toList());

    }



}


//    @Override
//    public String checkingAnswer(Map<Integer,String> requestData) {
//        for (Map.Entry<Integer, String> entry : requestData.entrySet()) {
//            Integer key = entry.getKey();
//            String value = entry.getValue();
//            if ("quesid".equals(key)) {
//                Integer quesid = Integer.valueOf(value);
//                QuestionsConfig questionsConfig = questionListRepository.findById(quesid)
//                        .orElseThrow(() -> new ResourceNotFoundException("question is not found with this id: " + quesid));
//                String answer = questionsConfig.getAnswer();
//                if (requestData.get("selectedOption").equals(answer)) {
//                    return "answer is correct";
//                } else {
//                    return "answer is incorrect";
//                }
//            }
//        }
//        throw new IllegalArgumentException("Invalid request data");
//    }
//}


//    @Override
//    public String checkingAnswer(Integer questionId,String selectedOption) {
//        QuestionsConfig questionsConfig=questionListRepository.findById(questionId).orElseThrow(()->new ResourceNotFoundException("question is not found with this id:"+questionId));
//      String answer=questionsConfig.getAnswer();
//      if(selectedOption.equals(answer)){
//          return "answer is correct" ;
//      }
//      else{
//          return "answer is incorrect";}
//    }



//


//    @Override
//    public AnswerCheckResponse checkingAnswers(Long userId,AnswerCheckRequest requestData) {
//
//        UserDetails userDetails=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user id not found:"+userId));
//
//        Map<Integer, String> results = new HashMap<>();
//        UserScore userScore=new UserScore();
//        userScore.setScore(0);
//        userScore.setCorrect(0);
//        userScore.setInCorrect(0);
//        userScore.setNotVisited(0);
//        userScore.setTotal(0);
//        userScore.setUserDetails(userDetails);
//        userScore.setFirstName(userDetails.getFirstName());
//        userScore.setLastName(userDetails.getLastName());
//        //  UserScore userScore=userScoreRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("user not found with this provided id:"+id));
//        for (Map.Entry<Integer, String> entry : requestData.getSelectedOption().entrySet()) {
//            Integer questionId = entry.getKey();
//            String selectedOption = entry.getValue();
//            // Fetch the question using the provided questionId
//            QuestionsConfig questionsConfig = questionListRepository.findById(questionId)
//                    .orElseThrow(() -> new ResourceNotFoundException("question is not found with this id: " + questionId));
//            // Get the correct answer for the question
//            String answer = questionsConfig.getAnswer();
//
//            userScore.setTotal(userScore.getTotal() + 1);
//            // Compare the selected option with the correct answer
//
//            if (selectedOption == null || selectedOption.isEmpty()) {
//                userScore.setNotVisited(userScore.getNotVisited() + 1);
//                results.put(questionId, "not visited");
//            } else if (selectedOption.equals(answer)) {
//                userScore.setCorrect(userScore.getCorrect() + 1);
//                userScore.setScore(userScore.getScore() + 10);
//                results.put(questionId, "answer is correct");
//            } else {
//                userScore.setInCorrect(userScore.getInCorrect() + 1);
//                userScore.setScore(userScore.getScore() - 5);
//                results.put(questionId, "answer is incorrect");
//            }
//        }
//
//        userScoreRepository.save(userScore);
//
//        // Create a response object
//        AnswerCheckResponse response = new AnswerCheckResponse();
//        response.setResult(results);
//        response.setScore(userScore.getScore());
//        response.setTotal(userScore.getTotal());
//        response.setCorrect(userScore.getCorrect());
//        response.setInCorrect(userScore.getInCorrect());
//        response.setNotVisited(userScore.getNotVisited());
//        return response;
//    }

//    @Override
//    public AnswerCheckResponse checkingAnswers(Long userId,AnswerCheckRequest requestData,Long subjectId) {
//
//        UserDetails userDetails=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user id not found:"+userId));
//        Subject subject=subjectRepository.findById(subjectId).orElseThrow(()->new ResourceNotFoundException("subject id not found:"+subjectId));
//
//        Map<Integer, String> results = new HashMap<>();
//        UserScore userScore=new UserScore();
//        userScore.setScore(0);
//        userScore.setCorrect(0);
//        userScore.setInCorrect(0);
//        userScore.setNotVisited(0);
//        userScore.setTotal(0);
//        userScore.setUserDetails(userDetails);
//        userScore.setFirstName(userDetails.getFirstName());
//        userScore.setLastName(userDetails.getLastName());
//        userScore.setSubject(subject);
//
//        //  UserScore userScore=userScoreRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("user not found with this provided id:"+id));
//        for (Map.Entry<Integer, String> entry : requestData.getSelectedOption().entrySet()) {
//            Integer questionId = entry.getKey();
//            String selectedOption = entry.getValue();
//            // Fetch the question using the provided questionId
//            QuestionsConfig questionsConfig = questionListRepository.findById(questionId)
//                    .orElseThrow(() -> new ResourceNotFoundException("question is not found with this id: " + questionId));
//            // Get the correct answer for the question
//            String answer = questionsConfig.getAnswer();
//
//            userScore.setTotal(userScore.getTotal() + 1);
//            // Compare the selected option with the correct answer
//
//            if (selectedOption == null || selectedOption.isEmpty()) {
//                userScore.setNotVisited(userScore.getNotVisited() + 1);
//                results.put(questionId, "not visited");
//            } else if (selectedOption.equals(answer)) {
//                userScore.setCorrect(userScore.getCorrect() + 1);
//                userScore.setScore(userScore.getScore() + 10);
//                results.put(questionId, "answer is correct");
//            } else {
//                userScore.setInCorrect(userScore.getInCorrect() + 1);
//                userScore.setScore(userScore.getScore() - 5);
//                results.put(questionId, "answer is incorrect");
//            }
//        }
//
//        userScoreRepository.save(userScore);
//
//        // Create a response object
//        AnswerCheckResponse response = new AnswerCheckResponse();
//        response.setResult(results);
//        response.setScore(userScore.getScore());
//        response.setTotal(userScore.getTotal());
//        response.setCorrect(userScore.getCorrect());
//        response.setInCorrect(userScore.getInCorrect());
//        response.setNotVisited(userScore.getNotVisited());
//        return response;
//    }







//    @Override
//    public Optional<UserDetails> findUserById(Long id) {
//        return userRepository.findById(id);
//    }














//        }
//        userScoreRepository.save(userScore);

//            if (selectedOption.equals(answer)) {
//                results.put(questionId, "answer is correct");
//            } else {
//                results.put(questionId, "answer is incorrect");
//            }


//    @Override
//    public QuestionsConfig addQuestionToSubject(long subjectId, QuestionsConfig question) {
//        Subject subject = subjectRepository.findById(subjectId)
//                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id " + subjectId));
//        question.setSubject(subject);
//        return questionListRepository.save(question);
//    }

