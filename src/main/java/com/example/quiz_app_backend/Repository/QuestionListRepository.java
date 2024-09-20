package com.example.quiz_app_backend.Repository;

import com.example.quiz_app_backend.Entity.QuestionsConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionListRepository extends JpaRepository<QuestionsConfig,Integer> {

    // Fetch 20 random questions from the database
    @Query(value = "SELECT * FROM questions_config ORDER BY RANDOM() LIMIT 20", nativeQuery = true)
    List<QuestionsConfig> findRandomQuestions();

}

