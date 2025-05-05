package com.example.Final_Project.controllers;

import com.example.Final_Project.models.Question;
import com.example.Final_Project.services.QuestionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public Question addQuestion(@RequestBody Question question) {
        return questionService.add(question);
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable int id) {
        return questionService.delete(id);
    }

    @PutMapping("/{id}")
    public Question updateQuestion(@PathVariable int id, @RequestBody Question question) {
        return questionService.update(id, question);
    }
}
