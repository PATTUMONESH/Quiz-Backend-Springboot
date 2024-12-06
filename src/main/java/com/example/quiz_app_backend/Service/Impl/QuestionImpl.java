package com.example.quiz_app_backend.Service.Impl;

import com.example.quiz_app_backend.Dto.ImageResponse;
import com.example.quiz_app_backend.Dto.QuestionCreateDto;
import com.example.quiz_app_backend.Dto.QuestionResponseDto;
import com.example.quiz_app_backend.Dto.QuestionUpdateDto;
import com.example.quiz_app_backend.Entity.QuestionsConfig;
import com.example.quiz_app_backend.Entity.Subject;
import com.example.quiz_app_backend.Entity.TypeDefinition;
import com.example.quiz_app_backend.Entity.UserDetails;
import com.example.quiz_app_backend.Exception.ResourceNotFoundException;
import com.example.quiz_app_backend.Repository.QuestionListRepository;
import com.example.quiz_app_backend.Repository.SubjectRepository;
import com.example.quiz_app_backend.Repository.TypeDefinitionRepository;
import com.example.quiz_app_backend.Repository.UserRepository;
import com.example.quiz_app_backend.Service.QuestionService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionImpl implements QuestionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TypeDefinitionRepository typeDefinitionRepository;

    @Autowired
    private QuestionListRepository questionListRepository;

    private static final String IMAGE_UPLOAD_DIR="D:/screenshots/";



    @Override
    public Optional<UserDetails> findUserById(Long id) {
        return userRepository.findById(id);
    }



    @Override
    public QuestionsConfig createQuestion(QuestionCreateDto questionCreateDto) {
        Subject subject = subjectRepository.findById(questionCreateDto.getSubjectId()).orElseThrow(() -> new ResourceNotFoundException("Subject not found with this id"));
        TypeDefinition questionType=typeDefinitionRepository.findById(questionCreateDto.getQuestionType()).orElseThrow(()->new ResourceNotFoundException("question type id not found"));
        TypeDefinition option1Type=typeDefinitionRepository.findById(questionCreateDto.getOption1Type()).orElseThrow(()->new ResourceNotFoundException("option1 type id not found"));
        TypeDefinition option2Type=typeDefinitionRepository.findById(questionCreateDto.getOption2Type()).orElseThrow(()->new ResourceNotFoundException("option2 type id not found"));
        TypeDefinition option3Type=typeDefinitionRepository.findById(questionCreateDto.getOption3Type()).orElseThrow(()->new ResourceNotFoundException("option3 type id not found"));
        TypeDefinition option4Type=typeDefinitionRepository.findById(questionCreateDto.getOption4Type()).orElseThrow(()->new ResourceNotFoundException("option4 type id not found"));
        TypeDefinition answerType=typeDefinitionRepository.findById(questionCreateDto.getAnswerType()).orElseThrow(()->new ResourceNotFoundException("answer type id not found"));

        QuestionsConfig questionsConfig = new QuestionsConfig();
        questionsConfig.setSubject(subject);

        questionsConfig.setQuestionType(questionType);
        questionsConfig.setOption1Type(option1Type);
        questionsConfig.setOption2Type(option2Type);
        questionsConfig.setOption3Type(option3Type);
        questionsConfig.setOption4Type(option4Type);
        questionsConfig.setAnswerType(answerType);
        questionsConfig.setQuestion(questionCreateDto.getQuestion());
        questionsConfig.setOption1(questionCreateDto.getOption1());
        questionsConfig.setOption2(questionCreateDto.getOption2());
        questionsConfig.setOption3(questionCreateDto.getOption3());
        questionsConfig.setOption4(questionCreateDto.getOption4());
        questionsConfig.setAnswer(questionCreateDto.getAnswer());
        return questionListRepository.save(questionsConfig);
    }

    // Similar logic for updating questions with image or text
    @Override
    public QuestionsConfig updateQuestion(Integer id, QuestionUpdateDto questionUpdateDto) {
        QuestionsConfig questionsConfig = questionListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found for this id"));
        // Fetch related entities from the repositories based on DTO ids
        Subject subject = subjectRepository.findById(questionUpdateDto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with this id"));

        TypeDefinition questionType = typeDefinitionRepository.findById(questionUpdateDto.getQuestionType())
                .orElseThrow(() -> new ResourceNotFoundException("Question type not found"));

        TypeDefinition option1Type = typeDefinitionRepository.findById(questionUpdateDto.getOption1Type())
                .orElseThrow(() -> new ResourceNotFoundException("Option1 type not found"));

        TypeDefinition option2Type = typeDefinitionRepository.findById(questionUpdateDto.getOption2Type())
                .orElseThrow(() -> new ResourceNotFoundException("Option2 type not found"));

        TypeDefinition option3Type = typeDefinitionRepository.findById(questionUpdateDto.getOption3Type())
                .orElseThrow(() -> new ResourceNotFoundException("Option3 type not found"));

        TypeDefinition option4Type = typeDefinitionRepository.findById(questionUpdateDto.getOption4Type())
                .orElseThrow(() -> new ResourceNotFoundException("Option4 type not found"));

        TypeDefinition answerType = typeDefinitionRepository.findById(questionUpdateDto.getAnswerType())
                .orElseThrow(() -> new ResourceNotFoundException("Answer type not found"));

        // Update the fields in the existing question entity
        questionsConfig.setSubject(subject);
        questionsConfig.setQuestionType(questionType);
        questionsConfig.setOption1Type(option1Type);
        questionsConfig.setOption2Type(option2Type);
        questionsConfig.setOption3Type(option3Type);
        questionsConfig.setOption4Type(option4Type);
        questionsConfig.setAnswerType(answerType);
        questionsConfig.setQuestion(questionUpdateDto.getQuestion());
        questionsConfig.setOption1(questionUpdateDto.getOption1());
        questionsConfig.setOption2(questionUpdateDto.getOption2());
        questionsConfig.setOption3(questionUpdateDto.getOption3());
        questionsConfig.setOption4(questionUpdateDto.getOption4());
        questionsConfig.setAnswer(questionUpdateDto.getAnswer());

        return questionListRepository.save(questionsConfig);
    }



    @Override
    public ImageResponse imageUpload(MultipartFile image) throws IOException {
        String directory ="D:/screenshots/";
        String fileName ="";
        if(null!=image){
            String extension = FilenameUtils.getExtension((image.getOriginalFilename()));
            fileName = UUID.randomUUID()+"."+extension;
            Path fileNameAndPath = Paths.get(directory, fileName);
            Files.write(fileNameAndPath, image.getBytes());
        }
        ImageResponse imageResponse = new ImageResponse();
        imageResponse.setStatus(true);
        imageResponse.setCode(HttpStatus.OK.value());
        imageResponse.setMessage("File Upload Successfully");
        imageResponse.setImageName(fileName);
        return imageResponse;
    }

    public byte[] getImage(String imageName) throws IOException {
        String directory = "D:/screenshots/";
        Path filePath = Paths.get(directory, imageName);
        if (Files.exists(filePath)) {
            return Files.readAllBytes(filePath);
        } else {
            throw new FileNotFoundException("Image not found with name: " + imageName);
        }
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

                    String questionText;
                    if("image".equalsIgnoreCase(String.valueOf(question.getQuestionType()))){
                        questionText="Image: "+question.getQuestion();
                    }else{
                        questionText=question.getQuestion();
                    }

                    System.out.println("=================5===============");
                    for(String op:options){
                        System.out.println(op);
                    }

                    return new QuestionResponseDto(
                            question.getQuesid(),
                            questionText,
                            options.get(0),
                            options.get(1),
                            options.get(2),
                            options.get(3),
                            question.getAnswer(),
                            question.getSubject().getId(),
                            question.getQuestionType().getTypeId(),
                            question.getOption1Type().getTypeId(),
                            question.getOption2Type().getTypeId(),
                            question.getOption3Type().getTypeId(),
                            question.getOption4Type().getTypeId(),
                            question.getAnswerType().getTypeId()
                    );
                })
                .collect(Collectors.toList());
    }



//    @Override
//    public List<QuestionResponseDto> getAllQuesForAdmin(){
//        List<QuestionsConfig> obj=questionListRepository.findAll();
//        for(QuestionsConfig ob:obj){
//            System.out.println(ob);
//        }
//        return obj.stream()
//                .map(question -> new QuestionResponseDto(
//                        question.getQuesid(),
//                        question.getQuestion(),
//                        question.getOption1(),
//                        question.getOption2(),
//                        question.getOption3(),
//                        question.getOption4(),
//                        question.getAnswer(),
//                        question.getSubject().getId(),
//                        question.getQuestionType().getTypeId(),
//                        question.getOption1Type().getTypeId(),
//                        question.getOption2Type().getTypeId(),
//                        question.getOption3Type().getTypeId(),
//                        question.getOption4Type().getTypeId(),
//                        question.getAnswerType().getTypeId() // Get the ID of the associated subject
//                ))
//                .collect(Collectors.toList());
//    }





    public Page<QuestionResponseDto> getAllQuesForAdmin(Pageable pageable) {
        Page<QuestionsConfig> questionsPage = questionListRepository.findAll(pageable);

        return questionsPage.map(question -> new QuestionResponseDto(
                question.getQuesid(),
                question.getQuestion(),
                question.getOption1(),
                question.getOption2(),
                question.getOption3(),
                question.getOption4(),
                question.getAnswer(),
                question.getSubject().getId(),
                question.getQuestionType().getTypeId(),
                question.getOption1Type().getTypeId(),
                question.getOption2Type().getTypeId(),
                question.getOption3Type().getTypeId(),
                question.getOption4Type().getTypeId(),
                question.getAnswerType().getTypeId()
        ));
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



//@Override
//    public QuestionsConfig createQuestion(QuestionCreateDto questionCreateDto) {
//        Subject subjectId = subjectRepository.findById(questionCreateDto.getSubjectId()).orElseThrow(() -> new ResourceNotFoundException("subject not found with this id"));
//        QuestionsConfig questionsConfig = new QuestionsConfig();
//
//
////        if("image".equalsIgnoreCase(questionCreateDto.getQuestionType()) && questionCreateDto.getImage()!=null){
////            String imageName=saveImageToFileSystem(questionCreateDto.getImage());
////            questionsConfig.setQuestion(imageName);
////            questionsConfig.setQuestionType(questionCreateDto.getQuestionType());
////
////        }else{
////            questionsConfig.setQuestion(questionCreateDto.getQuestion());
////        }
//        questionsConfig.setSubject(subjectId);
//        //questionsConfig.setQuestion(questionCreateDto.getQuestion());
//        questionsConfig.setAnswer(questionCreateDto.getAnswer());
//        questionsConfig.setOption1(questionCreateDto.getOption1());
//        questionsConfig.setOption2(questionCreateDto.getOption2());
//        questionsConfig.setOption3(questionCreateDto.getOption3());
//        questionsConfig.setOption4(questionCreateDto.getOption4());
//
//
//        if (questionCreateDto.getQuestion().endsWith(".jpg") ||
//                questionCreateDto.getQuestion().endsWith(".png") ||
//                questionCreateDto.getQuestion().endsWith(".jpeg")) {
//            questionsConfig.setQuestionType("image");
//        } else {
//            questionsConfig.setQuestionType("text");
//        }
//        return questionListRepository.save(questionsConfig);
//    }
//
//    private String saveImageToFileSystem(MultipartFile imageFile){
//
//       try{
//           String originalFileName=imageFile.getOriginalFilename();
//           Path filePath= Paths.get(IMAGE_UPLOAD_DIR,originalFileName);
//           Files.copy(imageFile.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
//           return originalFileName;
//       } catch (IOException e) {
//           throw new RuntimeException("Error savin image"+e);
//       }
//
//
//    }
//
//
//
//    @Override
//    public QuestionsConfig updateQuestion(Integer id,QuestionUpdateDto questionUpdateDto) {
//        QuestionsConfig questionsConfig = questionListRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question not found for this id :: " + id));
//
//        if("image".equalsIgnoreCase(questionUpdateDto.getQuestionType()) && questionUpdateDto.getImage()!=null){
//            String imageName=saveImageToFileSystem(questionUpdateDto.getImage());
//            questionsConfig.setQuestionType(imageName);
//        }else {
//            questionsConfig.setQuestionType(questionUpdateDto.getQuestion());
//        }
//
//
//
//        //Subject subject = subjectRepository.findById(questionsConfig.getSubject().getId()).orElseThrow(() -> new ResourceNotFoundException("subject not found with this id"));
//
//
//
//        //questionsConfig.setQuestion(questionUpdateDto.getQuestion());
//
//
//
//
//        questionsConfig.setOption1(questionUpdateDto.getOption1());
//        questionsConfig.setOption2(questionUpdateDto.getOption2());
//        questionsConfig.setOption3(questionUpdateDto.getOption3());
//        questionsConfig.setOption4(questionUpdateDto.getOption4());
//        questionsConfig.setAnswer(questionUpdateDto.getAnswer());
////        questionsConfig.setSubject(subject);
////        final QuestionsConfig updatedQuestion = questionListRepository.save(questionsConfig);
////        return questionsConfig;
//        return questionListRepository.save(questionsConfig);
//    }




//        if ("image".equalsIgnoreCase(questionCreateDto.getQuestionType()) && questionCreateDto.getImage() != null) {
//            // Handle image file upload
//            String imageName = saveImageToFileSystem(questionCreateDto.getImage());
//            questionsConfig.setQuestion(imageName);  // Save image name in the question field
//        } else {
//            questionsConfig.setQuestion(questionCreateDto.getQuestion());  // Save text
//        }
// Set the rest of the fields

//        questionsConfig.setQuestionType(questionCreateDto.getQuestionType());


//
