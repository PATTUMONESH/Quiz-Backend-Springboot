package com.example.quiz_app_backend.Dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
public class AnswerCheckRequest {
    private Map<Integer,String> selectedOption;
}
