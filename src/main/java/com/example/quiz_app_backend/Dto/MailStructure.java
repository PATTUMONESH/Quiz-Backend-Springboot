package com.example.quiz_app_backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailStructure {
    private String name;
    private String subject;
    private String message;
    private long score;
    private int correct;
    private int inCorrect;
    private int notVisited;
    private int total;
    private int visitedQues;
    private String description;
}
