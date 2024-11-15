package com.example.quiz_app_backend.Service.Impl;

import com.example.quiz_app_backend.Dto.UserDetailsDto;
import com.example.quiz_app_backend.Entity.*;
import com.example.quiz_app_backend.Exception.ResourceNotFoundException;
import com.example.quiz_app_backend.Repository.RoleRepository;
import com.example.quiz_app_backend.Repository.UserRepository;
import com.example.quiz_app_backend.Repository.UserScoreRepository;
import com.example.quiz_app_backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

//    @Autowired
//    private JavaMailSender javaMailSender;

    private UserScoreRepository userScoreRepository;

//    @Autowired
//    private Configuration freemarkerConfig;

//    @Value("${spring.mail.username}")
//    private String fromMail;

//    @Override
//    public void sendMailUser(String mail,MailStructure mailStructure) {
//        try {
//            Map<String, Object> model = new HashMap<>();
//            model.put("name", mailStructure.getName());
//            model.put("score", mailStructure.getScore());
//            model.put("description", mailStructure.getDescription());
//            model.put("correctAnswer", mailStructure.getCorrect());
//            model.put("wrongAnswer", mailStructure.getInCorrect());
//            model.put("unAttemptedQuestion", mailStructure.getNotVisited());
//            model.put("AttemptedQuestions",mailStructure.getVisitedQues());
//            model.put("totalQuestions", mailStructure.getTotal());
//
//            Template template = freemarkerConfig.getTemplate("scoreEmailTemplate.ftl");
//            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
//
//            MimeMessage message = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setFrom(fromMail);
//            helper.setTo(mail);
//            helper.setSubject(mailStructure.getSubject());
//            helper.setText(html, true);
//            javaMailSender.send(message);
//        } catch (MessagingException | IOException | TemplateException e) {
//            e.printStackTrace();
//        }
//    }

//    @Override
//    public UserDetails saveUser(UserDetails userDetails) {
//        return userRepository.save(userDetails);
//    }


    @Override
    public UserDetails saveUser(UserDetailsDto userDetailsDto) {
        Role role=roleRepository.findById(userDetailsDto.getRoleId()).orElseThrow(()->new

                ResourceNotFoundException("role not found with this id"));
        UserDetails userDetails=new UserDetails();
        userDetails.setFirstName(userDetailsDto.getFirstName());
        userDetails.setLastName(userDetailsDto.getLastName());
        userDetails.setGender(userDetailsDto.getGender());
        userDetails.setEmail(userDetailsDto.getEmail());
        userDetails.setPassword(userDetailsDto.getPassword());
        userDetails.setPhno(userDetailsDto.getPhno());
        userDetails.setAddress(userDetailsDto.getAddress());
        userDetails.setRole(role);

        return userRepository.save(userDetails);
    }



    @Override
    public UserDetails fetchUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails
    fetchUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public Optional<UserDetails> findAdminById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public String getUserEmailById(Long userId) {
        UserDetails userDetails = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found ::" + userId));
        return userDetails.getEmail();
    }

    @Override
    public UserScore saveUserScore(UserScore userScore, Long userId) {
        UserDetails userDetails = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found ::" + userId));
        userScore.setFirstName(userDetails.getFirstName());
        userScore.setLastName(userDetails.getLastName());

        userScore.setUserDetails(userDetails);
        return userScoreRepository.save(userScore);
    }

    @Override
    public UserDetails findByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user is not found with this id:"+userId));
    }

//    @Override
//    public List<Role> getAllUserRoles() {
//        List<Role> role=roleRepository.findAll();
//        return role;
//    }




}

//    @Override
//    public UserScore saveUserScore(UserScore userScore, Long userId) {
//        UserDetails userDetails = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found ::" + userId));
//        userScore.setFirstName(userDetails.getFirstName());
//        userScore.setLastName(userDetails.getLastName());
//        userScore.setUserDetails(userDetails);
//        return userScoreRepository.save(userScore);
//    }

//    @Override
//    public List<UserScore> getAllUserScores() {
//        return userScoreRepository.findAll();
//    }

//    @Override
//    public QuestionsConfig createQuestion(QuestionDto questionDto) {
//        Subject subject = subjectRepository.findById(questionDto.getSubjectId()).orElseThrow(() -> new ResourceNotFoundException("subject not found with this id"));
//        QuestionsConfig questionsConfig = new QuestionsConfig();
//        questionsConfig.setQuestion(questionDto.getQuestion());
//        questionsConfig.setAnswer(questionDto.getAnswer());
//        questionsConfig.setOption1(questionDto.getOption1());
//        questionsConfig.setOption2(questionDto.getOption2());
//        questionsConfig.setOption3(questionDto.getOption3());
//        questionsConfig.setOption4(questionDto.getOption4());
//        questionsConfig.setSubject(subject);
//        return questionListRepository.save(questionsConfig);
//    }

//    @Override
//    public QuestionDto updateQuestion(Integer id, QuestionDto questionDto) {
//        QuestionsConfig questionsConfig = questionListRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question not found for this id :: " + id));
//        Subject subject = subjectRepository.findById(questionsConfig.getSubject().getId()).orElseThrow(() -> new ResourceNotFoundException("subject not found with this id"));
//        questionsConfig.setQuestion(questionDto.getQuestion());
//        questionsConfig.setAnswer(questionDto.getAnswer());
//        questionsConfig.setOption1(questionDto.getOption1());
//        questionsConfig.setOption2(questionDto.getOption2());
//        questionsConfig.setOption3(questionDto.getOption3());
//        questionsConfig.setOption4(questionDto.getOption4());
//        questionsConfig.setSubject(subject);
//        final QuestionsConfig updatedQuestion = questionListRepository.save(questionsConfig);
//        return questionDto;
//    }

//    @Override
//    public Optional<QuestionsConfig> getQuestionByQid(Integer Id) {
//        return questionListRepository.findById(Id);
//    }

//    @Override
//    public List<Subject> getAllSubjects() {
//        List<Subject> sub = subjectRepository.findAll();
//        return sub;
//    }

//    @Override
//    public List<QuestionsConfig> getAllQuestions() {
//        return questionListRepository.findAll();
//    }

//    @Override
//    public void deleteQuestion(Integer id) {
//        QuestionsConfig deletedQues = questionListRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question not found for this id :: +id"));
//        questionListRepository.delete(deletedQues);
//    }

//    @Override
//    public Subject saveSubject(Subject subject) {
//        return subjectRepository.save(subject);
//    }

//    @Override
//    public QuestionsConfig addQuestionToSubject(long subjectId, QuestionsConfig question) {
//        Subject subject = subjectRepository.findById(subjectId)
//                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id " + subjectId));
//        question.setSubject(subject);
//        return questionListRepository.save(question);
//    }


//    @Override
//    public QuestionsConfig addQuestionToSubject(long subjectId, QuestionsConfig question) {
//        Subject subject = subjectRepository.findById(subjectId)
//                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id " + subjectId));
//        question.setSubject(subject);
//        return questionListRepository.save(question);
//    }



//        List<QuestionDto> quesdto=new ArrayList<>();
//        for(QuestionsConfig q:ques){
//
////            Subject subject=q.getSubject();
////            SubjectDto subjectDto=new SubjectDto(subject.getId(),subject.getSubject());
//
//            quesdto.add(new QuestionDto(q.getQuesid(),q.getQuestion(),q.getAnswer(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4(),));
//



//    public QuestionsConfig saveQuestions(QuestionsConfig questionsConfig){
//        return questionListRepository.save(questionsConfig);
//    }


//     public UserImpl(UserRepository userRepository,UserScoreRepository userScoreRepository){
//        this.userScoreRepository=userScoreRepository;
//        this.userRepository=userRepository;
//     }


//    @Override
//    public UserDetails fetchUserEmailById() {
//        return userRepository.findByEmailId();
//    }


//        List<SubjectDto> subDto=new ArrayList<>();
//        // List<SubjectDto> subjectDtos = subjectMapper.listOfSubToListOfSubDto(sub);
//        for(Subject s:sub){
//            subDto.add(new SubjectDto(s.getId(),s.getSubject()));
//        }