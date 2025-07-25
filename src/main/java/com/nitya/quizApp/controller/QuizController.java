package com.nitya.quizApp.controller;

import com.nitya.quizApp.model.QuestionWrapper;
import com.nitya.quizApp.model.QuizResponse;
import com.nitya.quizApp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;
    @PreAuthorize("hasRole('teacher')")
    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam Integer numQ, @RequestParam String title){

        return quizService.createQuiz(category, numQ, title);

    }
    //This endpoint can be used by bothe student & teacher
    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
            return quizService.getQuizQuestions(id);

    }

    @PreAuthorize("hasRole('student')")
    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<QuizResponse> responses){
        return  quizService.submitAndCalculateQuiz(id, responses);
    }
}
