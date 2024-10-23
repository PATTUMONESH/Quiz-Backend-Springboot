package com.example.quiz_app_backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserScoreDto {
    private long id;
    private String firstName;
    private String lastName;
    private int score;

}
