package com.example.quiz_app_backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionResponseDto {
    private Integer quesid;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;
    private Long subjectId;
    private Long questionType;
    private Long option1Type;
    private Long option2Type;
    private Long option3Type;
    private Long option4Type;
    private Long answerType;



}
