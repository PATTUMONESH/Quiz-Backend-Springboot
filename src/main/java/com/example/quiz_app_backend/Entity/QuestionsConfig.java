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
    private String questionType;
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Override
    public String toString() {
        return "QuestionsConfig{" +
                "quesid=" + quesid +
                ", question='" + question + '\'' +
                ", option1='" + option1 + '\'' +
                ", option2='" + option2 + '\'' +
                ", option3='" + option3 + '\'' +
                ", option4='" + option4 + '\'' +
                ", answer='" + answer + '\'' +
                ", questionType='" + questionType + '\'' +
                ", subject=" + subject +
                '}';
    }
}
