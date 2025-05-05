package com.example.Final_Project.services;

import com.example.Final_Project.models.Question;
import com.example.Final_Project.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question add(Question question) {
        return questionRepository.save(question);
    }

    public String delete(int id) {
        if(questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return "Question deleted";
        }
        return "Question not found";

    }

    public Question update(int id, Question question) {
        if(questionRepository.existsById(id)) {
            question.setId(id);
           return questionRepository.save(question);
        }
        return null;
    }

    public Question findById(int id) {
        return questionRepository.findById(id).orElse(null);
    }
}
