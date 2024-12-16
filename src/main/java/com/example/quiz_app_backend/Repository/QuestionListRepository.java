package com.example.quiz_app_backend.Repository;

import com.example.quiz_app_backend.Entity.QuestionsConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionListRepository extends JpaRepository<QuestionsConfig,Integer> {

    @Query(value = "(SELECT * FROM questions_config WHERE subject_id = 1 ORDER BY RANDOM() LIMIT 20) UNION ALL (SELECT * FROM questions_config WHERE subject_id = 2 ORDER BY RANDOM() LIMIT 10)UNION ALL (SELECT * FROM questions_config WHERE subject_id = 3 ORDER BY RANDOM() LIMIT 10)", nativeQuery = true)
    List<QuestionsConfig> findRandomQuestions();

    
    Page<QuestionsConfig> findAll(Pageable pageable);

}

