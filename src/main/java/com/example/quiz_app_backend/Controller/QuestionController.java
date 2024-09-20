package com.example.quiz_app_backend.Controller;

import com.example.quiz_app_backend.Dto.*;
import com.example.quiz_app_backend.Entity.QuestionsConfig;
import com.example.quiz_app_backend.Exception.ResourceNotFoundException;
import com.example.quiz_app_backend.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins ="*",allowedHeaders = "*")
@RequestMapping()
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/getQuestion/{id}")
    public ResponseEntity<QuestionsConfig> getQuestionById(@RequestParam Integer questionId) {
        QuestionsConfig questionsConfig = questionService.getQuestionByQid(questionId).orElseThrow(() -> new ResourceNotFoundException("Question does not exists with this id"));
        return ResponseEntity.ok(questionsConfig);
    }
    @PostMapping("/addQuestions")
    public ResponseEntity<Response> addQuestion(@RequestBody QuestionCreateDto questionCreateDto) {

        questionService.createQuestion(questionCreateDto);
        Response response = new Response();
        response.setMessage("Questions added successfully");
        response.setStatus(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/updateQuestion")
    public ResponseEntity<Response> updateQuestionById(@RequestHeader Integer id, @RequestBody QuestionUpdateDto questionUpdateDto) {
        questionService.updateQuestion(id,questionUpdateDto);
        Response response=new Response();
        response.setMessage("question update successfully");
        response.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/deleteQuestion")
    public ResponseEntity<Map<String, Boolean>> deleteQuestion(@RequestHeader Integer id) {
        questionService.deleteQuestion(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("getAllQuestions")
//    public ResponseEntity<List<QuestionResponseDto>> getAllQuestions() {
//        List<QuestionResponseDto> questions = questionService.getAllQues();
//        return new ResponseEntity<>(questions, HttpStatus.OK);
//    }


    @GetMapping("getAllQuestionsForUser")
    public ResponseEntity<List<QuestionResponseDto>> getAllQuestionsForUser() {
        List<QuestionResponseDto> questions = questionService.getAllQuesForUser();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("getAllQuestionsForAdmin")
    public ResponseEntity<List<QuestionResponseDto>> getAllQuestionsForAdmin(){
        List<QuestionResponseDto> questionResponseDtos=questionService.getAllQuesForAdmin();
        return new ResponseEntity<>(questionResponseDtos,HttpStatus.OK);
    }



}

//    @GetMapping("/getAllQuestions")
//    public ResponseEntity<List<QuestionUpdateDto>> getAllQuestions() {
//        // Retrieve the list of QuestionsConfig from the service
//        List<QuestionsConfig> questionList = questionService.getAllQuestions();
//        // Map each QuestionsConfig to QuestionDto
//        List<QuestionUpdateDto> questionUpdateDtoList = questionList.stream().map(this::convertToDto).collect(Collectors.toList());
//        // Return the list of QuestionDto
//        return new ResponseEntity<>(questionUpdateDtoList, HttpStatus.OK);
//    }






//    private QuestionUpdateDto convertToDto(QuestionsConfig questionsConfig) {
//        // Create a new QuestionDto object
//        QuestionUpdateDto questionUpdateDto = new QuestionUpdateDto();
//        // Map the fields from QuestionsConfig to QuestionDto
//        questionUpdateDto.setQuesid(questionsConfig.getQuesid());
//        questionUpdateDto.setQuestion(questionsConfig.getQuestion());
//        questionUpdateDto.setOption1(questionsConfig.getOption1());
//        questionUpdateDto.setOption2(questionsConfig.getOption2());
//        questionUpdateDto.setOption3(questionsConfig.getOption3());
//        questionUpdateDto.setOption4(questionsConfig.getOption4());
//        questionUpdateDto.setSubjectId(questionsConfig.getSubject().getId());
//        return questionUpdateDto;
//    }






//    @GetMapping ("/user")
//    public ResponseEntity<UserDetails> findUserById(@RequestHeader Long id){
//
//        UserDetails response=questionService.findUserById(id).orElseThrow(()->new ResourceNotFoundException("not found "+id));
//
//        return new ResponseEntity<>(response,HttpStatus.OK);
//    }



//    @PostMapping("/checkAnswer")
//public ResponseEntity<Response> checkAnswer(@RequestBody Map<Integer,String> requestData){
//        String result=questionService.checkingAnswer(requestData);
//        Response response=new Response();
//        response.setMessage(result);
//        response.setStatus(HttpStatus.OK.value());
//        return new ResponseEntity<>(response,HttpStatus.OK);
//
//    }
//}


//    @PostMapping("/checkAnswer")
//    public ResponseEntity<Response> checkAnswer(@RequestHeader  Integer questionId,@RequestParam String selectedOption){
//        String result=questionService.checkingAnswer(questionId,selectedOption);
//        Response response=new Response();
//        response.setMessage(result);
//        response.setStatus(HttpStatus.OK.value());
//        return new ResponseEntity<>(response,HttpStatus.OK);
//    }


//@GetMapping("/getAllQuestions")
//    public ResponseEntity<List<QuestionsConfig>> getAllQuestions() {
//        List<QuestionsConfig> questionList=questionService.getAllQuestions();
//        return new ResponseEntity<>(questionList, HttpStatus.OK);
//    }



//@PostMapping("/checkAnswers")
//    public ResponseEntity<AnswerCheckResponse> checkAnswers(@RequestHeader("id") Long id,@RequestBody AnswerCheckRequest requestData,@RequestHeader Long subjectId) {
//        // Call the service method with the requestData DTO
//        AnswerCheckResponse response = questionService.checkingAnswers(id,requestData,subjectId);
//        // Return the ResponseEntity with the custom response object
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }


