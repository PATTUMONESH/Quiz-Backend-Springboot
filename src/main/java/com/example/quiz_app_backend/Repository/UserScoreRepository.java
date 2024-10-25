package com.example.quiz_app_backend.Repository;

import com.example.quiz_app_backend.Entity.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, Long>, JpaSpecificationExecutor<UserScore> {

@Query(value = "select * from user_score ORDER BY score_time_stamp DESC",nativeQuery = true)
  List<UserScore> findAllScoresByDesc();
  @Query(value = "SELECT * FROM user_score WHERE (LOWER(first_name) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(last_name) LIKE LOWER(CONCAT('%', :name, '%')))" +
          " OR (LOWER(CONCAT(first_name, ' ', last_name)) LIKE LOWER(CONCAT('%', :name, '%')))", nativeQuery = true)
  List<UserScore> findByFirstNameOrLastName(@Param("name") String name);


}


