//package com.example.quiz_app_backend.Service.Impl;
//
//
//import com.example.quiz_app_backend.Entity.UserDetails;
//import com.example.quiz_app_backend.Entity.UserScore;
//import com.example.quiz_app_backend.Service.EmailService;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import freemarker.template.Configuration;
//
//import java.io.IOException;
//import java.io.StringWriter;
//import java.util.HashMap;
//import java.util.Map;
//
//
//@Service
//public class EmailImpl implements EmailService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Autowired
//    private Configuration freemarkerConfig;
//
//
//    @Override
//    public void sendQuizResultsEmail(UserDetails userDetails, UserScore userScore) throws MessagingException, IOException, TemplateException {
//        freemarkerConfig.setClassForTemplateLoading(this.getClass(),"/templates");
//
//        Map<String,Object> model=new HashMap<>();
//        model.put("name",userDetails.getFirstName()+" "+userDetails.getLastName());
//        model.put("score",userScore.getTotal());
//        model.put("correctAnswer",userScore.getCorrect());
//        model.put("wrongAnswer",userScore.getCorrect());
//        model.put("unAttemptedQuestion",userScore.getNotVisited());
//        model.put("AttemptedQuestions",userScore.getVisitedQues());
//        model.put("totalQuestions",userScore.getTotal());
//
//        Template template=freemarkerConfig.getTemplate("scoreEmailTemplate.ftl");
//        StringWriter stringWriter=new StringWriter();
//        template.process(model,stringWriter);
//        String emailContent = stringWriter.getBuffer().toString();
//
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//        helper.setTo(userDetails.getEmail());
//        helper.setSubject("Your Quiz Results");
//        helper.setText(emailContent, true);
//
//        mailSender.send(message);
//
//
//
//
//
//    }
//}
