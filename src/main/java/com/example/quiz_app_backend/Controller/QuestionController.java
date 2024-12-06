package com.example.quiz_app_backend.Controller;

import com.example.quiz_app_backend.Dto.*;
import com.example.quiz_app_backend.Entity.QuestionsConfig;
import com.example.quiz_app_backend.Entity.UserDetails;
import com.example.quiz_app_backend.Exception.ResourceNotFoundException;
import com.example.quiz_app_backend.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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





//    @PostMapping("/uploadImage")
//    public ResponseEntity<ImageResponse> imageUpload(@RequestParam("image") MultipartFile image){
//        try {
//            ImageResponse imageResponse = questionService.imageUpload(image);
//            return new ResponseEntity<>(imageResponse,HttpStatus.OK);
//        }catch (IOException e){
//
//            ImageResponse errorResponse=new ImageResponse(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage(),null);
//
//            return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }



    @PostMapping("/uploadImage")
    public ResponseEntity<ImageResponse> imageUpload(@RequestParam("image") MultipartFile image) {
        // Check if the image file is empty
        if (image == null || image.isEmpty()) {
            ImageResponse errorResponse = new ImageResponse(false, HttpStatus.BAD_REQUEST.value(), "Please select the file", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            ImageResponse imageResponse = questionService.imageUpload(image);
            return new ResponseEntity<>(imageResponse, HttpStatus.OK);
        } catch (IOException e) {
            ImageResponse errorResponse = new ImageResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getImage")
  public  ResponseEntity<byte[]> getImage(@RequestParam String imageName) throws IOException {
        byte[] imageData= questionService.getImage(imageName);
        return new ResponseEntity<>(imageData,HttpStatus.OK);
    }



    @PutMapping("/updateQuestion")
    public ResponseEntity<Response> updateQuestionById(@RequestHeader Integer id, @RequestBody QuestionUpdateDto questionUpdateDto) {
        questionService.updateQuestion(id,questionUpdateDto);
        Response response=new Response();
        response.setMessage("question update successfully");
        response.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @GetMapping ("/user")
    public ResponseEntity<UserDetails> findUserById(@RequestHeader Long id){
   UserDetails response=questionService.findUserById(id).orElseThrow(()->new ResourceNotFoundException("user not found with this id: "+id));
   return new ResponseEntity<>(response,HttpStatus.OK);
    }




    @DeleteMapping("/deleteQuestion")
    public ResponseEntity<Map<String, Boolean>> deleteQuestion(@RequestHeader Integer id) {
        questionService.deleteQuestion(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }



    @GetMapping("getAllQuestionsForUser")
    public ResponseEntity<List<QuestionResponseDto>> getAllQuestionsForUser() {
        List<QuestionResponseDto> questions = questionService.getAllQuesForUser();
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }


//    @GetMapping("getAllQuestionsForAdmin")
//    public ResponseEntity<List<QuestionResponseDto>> getAllQuestionsForAdmin(){
//        List<QuestionResponseDto> questionResponseDtos=questionService.getAllQuesForAdmin();
//        return new ResponseEntity<>(questionResponseDtos,HttpStatus.OK);
//    }


    @GetMapping("/getAllQuestionsForAdmin")
    public ResponseEntity<Page<QuestionResponseDto>> getAllQuestionsForAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<QuestionResponseDto> questions = questionService.getAllQuesForAdmin(pageable);
        return new ResponseEntity<>(questions, HttpStatus.OK);
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











// @GetMapping("getAllQuestions")
//    public ResponseEntity<List<QuestionResponseDto>> getAllQuestions() {
//        List<QuestionResponseDto> questions = questionService.getAllQues();
//        return new ResponseEntity<>(questions, HttpStatus.OK);
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


