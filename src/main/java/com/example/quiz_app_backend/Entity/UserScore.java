package com.example.quiz_app_backend.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserScore {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_score_seq")
    @SequenceGenerator(name = "user_score_seq", sequenceName = "user_score_sequence", initialValue = 101, allocationSize = 1)
    private long id;
    private String firstName;
    
    private String lastName;
    private int score;
    private int correct;
    private int inCorrect;
    private int notVisited;
    private int total;
    private int visitedQues;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserDetails userDetails;

    @ManyToOne
    @JoinColumn(name = "subject_id",referencedColumnName = "id")
    private Subject subject;

}
