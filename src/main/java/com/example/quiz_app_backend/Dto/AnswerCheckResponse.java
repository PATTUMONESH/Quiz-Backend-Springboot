package com.example.quiz_app_backend.Dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class AnswerCheckResponse {
    private Map<Integer,String> result;
    private int score;
    private int correct;
    private int inCorrect;
    private int notVisited;
    private int total;
    private int visitedQues;
}
//    private Integer Status;
