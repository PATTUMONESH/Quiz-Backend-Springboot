package com.example.quiz_app_backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {
    private Boolean status;
    private Integer code;
    private String message;
    private String imageName;

}
