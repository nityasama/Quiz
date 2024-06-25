package com.nitya.quizApp.service;

import com.nitya.quizApp.dao.QuestionDao;
import com.nitya.quizApp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    public ResponseEntity<List<Question>> getAllQuestions(){
    try {
        return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
    }catch(Exception e){
        e.printStackTrace();
    }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }


    public List<Question> getQuestionsByCategory(String category) {
        return questionDao.findByCategory(category);
    }

    public Optional<Question> getQuestionsById(Integer id) {
        return questionDao.findById(id);
    }

    public ResponseEntity<String> addQuestion(Question question) {
    try {
        questionDao.save(question);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }catch(Exception e){
        e.printStackTrace();
    }

    return new ResponseEntity<>("bad request", HttpStatus.BAD_REQUEST);

    }
}

