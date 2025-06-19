package com.nitya.quizApp.repository;

import com.nitya.quizApp.model.LoginAttempts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoginAttemptsRepository extends JpaRepository<LoginAttempts, Integer> {


    @Query("SELECT l FROM LoginAttempts l WHERE email = :email ORDER BY createdAt DESC LIMIT :recentCount")
    List<LoginAttempts> findTopByEmailOrderByCreatedAtDesc(String email, int recentCount);

}
