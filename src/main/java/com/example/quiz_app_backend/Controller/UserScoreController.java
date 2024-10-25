package com.example.quiz_app_backend.Controller;

import com.example.quiz_app_backend.Dto.AnswerCheckRequest;
import com.example.quiz_app_backend.Dto.AnswerCheckResponse;
import com.example.quiz_app_backend.Entity.UserDetails;
import com.example.quiz_app_backend.Entity.UserScore;
//import com.example.quiz_app_backend.Service.EmailService;
import com.example.quiz_app_backend.Service.SubjectService;
import com.example.quiz_app_backend.Service.UserScoreService;
import com.example.quiz_app_backend.Service.UserService;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin(origins ="*",allowedHeaders = "*")
@RequestMapping()
public class UserScoreController {

    @Autowired
    private UserScoreService userScoreService;
//    @Autowired
//    private EmailService emailService;
    @Autowired
    private UserService userService;
    @Autowired
    private SubjectService subjectService;



    @PostMapping("/checkAnswers")
    public ResponseEntity<AnswerCheckResponse> checkAnswers(@RequestHeader("id") Long userId,
                                                            @RequestBody AnswerCheckRequest requestData,
                                                            @RequestHeader Long subjectId) {
        try {
            // Process the answers and send the email
            AnswerCheckResponse response = userScoreService.checkingAnswers(userId, requestData, subjectId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (MessagingException | IOException | TemplateException e) {
            // Handle email sending errors
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllUserScores")
    public ResponseEntity<List<UserScore>> getAllUserScores(){
        List<UserScore> userScores=userScoreService.getAllUserScores();
        return new ResponseEntity<>(userScores,HttpStatus.OK);
    }



    @GetMapping("/search")
    public List<UserScore> searchUserScores(@RequestHeader String name) {
if(name.length()<3){
    throw new IllegalArgumentException("search term at least 3 letter");
}
return userScoreService.findByFirstNameOrLastName(name);
    }


    @DeleteMapping("/deleteUserScore")
    public ResponseEntity<Map<String,Boolean>> deleteUserScore(@RequestHeader Long id){
        userScoreService.deleteUserScoreById(id);
        Map<String,Boolean> response=new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }




}




//    @PostMapping("/checkAnswers")
//    public ResponseEntity<AnswerCheckResponse> checkAnswers(@RequestHeader("id") Long id, @RequestBody AnswerCheckRequest requestData, @RequestHeader Long subjectId) throws MessagingException, TemplateException, IOException {
//        // Call the service method with the requestData DTO
//        AnswerCheckResponse response = userScoreService.checkingAnswers(id, requestData, subjectId);
//        // Return the ResponseEntity with the custom response object
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }




//@PostMapping("/userscore/{userId}")
//public ResponseEntity<Response> saveUserScoreById(@RequestBody UserScore userScore, @PathVariable Long userId) {
//    userScoreService.saveUserScore(userScore, userId);
//    String email = userService.getUserEmailById(userId);
//    // Send email
//    MailStructure mailStructure = new MailStructure();
//    mailStructure.setSubject("Score Submission Confirmation");
//    mailStructure.setMessage("Your score is " + userScore.getScore());
//    mailStructure.setName(userScore.getFirstName().toUpperCase().concat(" ").concat(userScore.getLastName().toUpperCase()));
//    System.out.println(userScore.getFirstName());
//    mailStructure.setScore(userScore.getScore());
//    int score = userScore.getScore();
//    String description = score > 60 ? "Congratulations,you are shotlisted for next round" : "Better luck next time,you are not qualified in screening test";
//    System.out.println("Description: " + description);
//    mailStructure.setDescription(description);
//    System.out.println(userScore.getScore());
//    mailStructure.setCorrectAnswer(userScore.getCorrect());
//    System.out.println(userScore.getCorrect());
//    mailStructure.setWrongAnswer(userScore.getInCorrect());
//    System.out.println(userScore.getInCorrect());
//    mailStructure.setUnAtteptedQuestion(userScore.getNotVisited());
//    System.out.println(userScore.getNotVisited());
//    mailStructure.setTotalQuestions(userScore.getTotal());
//    System.out.println(userScore.getTotal());
//    userService.sendMailUser(email, mailStructure);
//    Response mailResponse = new Response();
//    mailResponse.setMessage("Score submitted and email sent successfully");
//    mailResponse.setStatus(HttpStatus.CREATED.value());
//    return new ResponseEntity<>(mailResponse, HttpStatus.OK);
//}



//    @GetMapping("/userscorelist")
//    public List<UserScore> getAllUserScores() {
//        return userScoreService.getAllUserScores();
//    }

