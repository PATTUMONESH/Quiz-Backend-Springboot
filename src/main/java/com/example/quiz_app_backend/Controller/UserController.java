package com.example.quiz_app_backend.Controller;

import com.example.quiz_app_backend.Dto.Response;
import com.example.quiz_app_backend.Dto.UserDetailsDto;
import com.example.quiz_app_backend.Dto.UserLoginDto;
import com.example.quiz_app_backend.Entity.*;
import com.example.quiz_app_backend.Exception.BadLoginCredentials;
import com.example.quiz_app_backend.Exception.ResourceNotFoundException;
import com.example.quiz_app_backend.Exception.UserAlreadyExistsException;
import com.example.quiz_app_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins ="*",allowedHeaders = "*")
@RequestMapping()
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/RegisterUsers")
    public ResponseEntity<Response> registerUser(@RequestBody UserDetailsDto userDetailsDto) {
        String tempEmailId = userDetailsDto.getEmail();
        if (tempEmailId != null && !"".equals(tempEmailId)) {
            UserDetails userObj = userService.fetchUserByEmail(tempEmailId);
            if (userObj != null) {
                throw new UserAlreadyExistsException("User with " + tempEmailId + " already exists");
            }
        }
        userService.saveUser(userDetailsDto);
        Response response = new Response();
        response.setMessage("Register successfully");
        response.setStatus(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }








    @PostMapping("/Login")
    public ResponseEntity<UserDetails> loginUser(@RequestBody UserLoginDto userLoginDto) {
        String tempEmail = userLoginDto.getEmail();
        String tempPassword = userLoginDto.getPassword();
        UserDetails userObject = null;
        if (tempEmail != null && tempPassword != null) {
            userObject = userService.fetchUserByEmailAndPassword(tempEmail, tempPassword);
        }
        if (userObject == null) {
            throw new BadLoginCredentials("invalid credentials");
        }
        Response response = new Response();
        return new ResponseEntity<>(userObject, HttpStatus.OK);
    }


    @GetMapping("/adminById")
    public ResponseEntity<UserDetails> adminDetailsById(@RequestParam Long id){

        UserDetails response=userService.findAdminById(id).orElseThrow(()->new ResourceNotFoundException("admin not found with this id: "+id));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }






}


























//    @GetMapping("/userRoles")
//    public ResponseEntity<List<Role>> getAllUserRoles(){
//        List<Role> userList=userService.getAllUserRoles();
//        return new ResponseEntity<>(userList,HttpStatus.OK);
//    }









//    @PostMapping("/userscore/{userId}")
//    public ResponseEntity<Response> saveUserScoreById(@RequestBody UserScore userScore, @PathVariable Long userId) {
//        userService.saveUserScore(userScore, userId);
//        String email = userService.getUserEmailById(userId);
//        // Send email
//        MailStructure mailStructure = new MailStructure();
//        mailStructure.setSubject("Score Submission Confirmation");
//        mailStructure.setMessage("Your score is " + userScore.getScore());
//        mailStructure.setName(userScore.getFirstName().toUpperCase().concat(" ").concat(userScore.getLastName().toUpperCase()));
//        System.out.println(userScore.getFirstName());
//        mailStructure.setScore(userScore.getScore());
//        int score = userScore.getScore();
//        String description = score > 60 ? "Congratulations,you are shotlisted for next round" : "Better luck next time,you are not qualified in screening test";
//        System.out.println("Description: " + description);
//        mailStructure.setDescription(description);
//        System.out.println(userScore.getScore());
//        mailStructure.setCorrect(userScore.getCorrect());
//        System.out.println(userScore.getCorrect());
//        mailStructure.setInCorrect(userScore.getInCorrect());
//        System.out.println(userScore.getInCorrect());
//        mailStructure.setNotVisited(userScore.getNotVisited());
//        System.out.println(userScore.getNotVisited());
//        mailStructure.setTotal(userScore.getTotal());
//        System.out.println(userScore.getTotal());
//        mailStructure.setVisitedQues(userScore.getVisitedQues());
//        userService.sendMailUser(email, mailStructure);
//        Response mailResponse = new Response();
//        mailResponse.setMessage("Score submitted and email sent successfully");
//        mailResponse.setStatus(HttpStatus.CREATED.value());
//        return new ResponseEntity<>(mailResponse, HttpStatus.OK);
//    }










//    @PostMapping("/userscore/{userId}")
//    public ResponseEntity<Response> saveUserScoreById(@RequestBody UserScore userScore, @PathVariable Long userId) {
//        userImpl.saveUserScore(userScore, userId);
//        String email=userImpl.getUserEmailById(userId);
//
//        // Send email
//        MailStructure mailStructure = new MailStructure();
//        mailStructure.setSubject("Score Submission Confirmation");
//        mailStructure.setMessage("Your score is "+userScore.getScore());
//        userImpl.sendMailUser(email, mailStructure);

//        Response mailResponse = new Response();
//        mailResponse.setMessage("Score submitted and email sent successfully");
//        mailResponse.setStatus(HttpStatus.CREATED.value());
//        return new ResponseEntity<>(mailResponse, HttpStatus.OK);
//        Response addScoreresponse = new Response();
//        addScoreresponse.setMessage("user Score added successful");
//        addScoreresponse.setStatus(HttpStatus.CREATED.value());
//       System.out.println(addScoreresponse.getStatus());
//        return new ResponseEntity<>(addScoreresponse, HttpStatus.OK);
// }



//    @GetMapping("/userscore/{userId}")
//    public ResponseEntity<Response> getUserById(@PathVariable long userId){
//    }



//    @PostMapping("/send/{mail}")
//    public ResponseEntity<Response> sendMail(@PathVariable String mail, @RequestBody MailStructure mailStructure){
//        userImpl.sendMailUser(mail,mailStructure);
//        Response mailResponse = new Response();
//        mailResponse.setMessage("mail sent successfully");
//        mailResponse.setStatus(HttpStatus.CREATED.value());
//        return new ResponseEntity<>(mailResponse, HttpStatus.OK);
//    }

//    @PostMapping("/addQuestions")
//    public ResponseEntity<Response> storeQuestions(@RequestBody QuestionsConfig questionsConfig) {
//        userImpl.saveQuestions(questionsConfig);
//        Response response = new Response();
//        response.setMessage("Questions added successfully");
//        response.setStatus(HttpStatus.CREATED.value());
//        return new ResponseEntity<>(response, HttpStatus.OK);
//
//    }




//    private UserImpl userImpl;
//    public UserController(UserImpl userImpl) {
//        this.userImpl = userImpl;
//    }



//

//    @GetMapping("/usescorelist")
//    public List<UserScore> getAllUserScores() {
//        return userService.getAllUserScores();
//    }

//    @PostMapping("/addQuestions")
//    public ResponseEntity<Response> addQuestion(@RequestBody QuestionDto questionDto) {
//        userService.createQuestion(questionDto);
//        Response response = new Response();
//        response.setMessage("Questions added successfully");
//        response.setStatus(HttpStatus.CREATED.value());
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }


//    @PostMapping("/addSubject")
//    public ResponseEntity<Response> addSubject(@RequestBody Subject subject){
//        userService.saveSubject(subject);
//        Response response = new Response();
//        response.setMessage("Question set added successfully");
//        response.setStatus(HttpStatus.CREATED.value());
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

//    @GetMapping("/getQuestion/{id}")
//    public ResponseEntity<QuestionsConfig> getQuestionById(@RequestParam Integer questionId) {
//        QuestionsConfig questionsConfig = questionService.getQuestionByQid(questionId).orElseThrow(() -> new ResourceNotFoundException("Question does not exists with this id"));
//        return ResponseEntity.ok(questionsConfig);
//    }
//
//    @PutMapping("/updateQuestion/{id}")
//    public ResponseEntity<QuestionDto> updateQuestionById(@PathVariable Integer id, @RequestBody QuestionDto questionRequest){
//        QuestionDto updateQuestion=questionService.updateQuestion(id,questionRequest);
//        return ResponseEntity.ok(updateQuestion);
//    }
//
//    @DeleteMapping("/deleteQuestion/{id}")
//    public ResponseEntity<Map<String,Boolean>> deleteQuestion(@PathVariable Integer id){
//        questionService.deleteQuestion(id);
//        Map<String,Boolean> response=new HashMap<>();
//        response.put("deleted",Boolean.TRUE);
//        return ResponseEntity.ok(response);
//    }

//    @GetMapping("/getAllSubjects")
//    public ResponseEntity<List<Subject>> getSubjects(){
//        List<Subject> subjectList=userService.getAllSubjects();
//        return new ResponseEntity<>(subjectList,HttpStatus.OK);
//    }

//    @GetMapping("/getAllQuestions")
//    public ResponseEntity<List<QuestionsConfig>> getAllQuestions() {
//        List<QuestionsConfig> questionList=questionService.getAllQuestions();
//        return new ResponseEntity<>(questionList,HttpStatus.OK);
//    }










