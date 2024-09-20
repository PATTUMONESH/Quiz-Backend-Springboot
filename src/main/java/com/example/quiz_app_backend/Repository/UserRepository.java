package com.example.quiz_app_backend.Repository;

import com.example.quiz_app_backend.Entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDetails,Long> {

    UserDetails findByEmail(String email);
    UserDetails findByEmailAndPassword(String email,String password);



}
