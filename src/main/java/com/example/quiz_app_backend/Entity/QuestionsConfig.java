package com.example.quiz_app_backend.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions_config")
public class QuestionsConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="questionId")
    private Integer quesid;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;

   @ManyToOne
    @JoinColumn(name="question_type",nullable = false,referencedColumnName = "typeId")
    private TypeDefinition questionType;
    @ManyToOne
    @JoinColumn(name="option1_type",nullable = false,referencedColumnName = "typeId")
    private TypeDefinition option1Type;
    @ManyToOne
    @JoinColumn(name="option2_type",nullable = false,referencedColumnName = "typeId")
    private TypeDefinition option2Type;
    @ManyToOne
    @JoinColumn(name="option3_type",nullable = false,referencedColumnName = "typeId")
    private TypeDefinition option3Type;
    @ManyToOne
    @JoinColumn(name="option4_type",nullable = false,referencedColumnName = "typeId")
    private TypeDefinition option4Type;

    @ManyToOne
    @JoinColumn(name="answer_type",nullable = false,referencedColumnName = "typeId")
    private TypeDefinition answerType;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

}
