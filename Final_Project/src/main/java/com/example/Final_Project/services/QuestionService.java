package com.example.Final_Project.services;

import com.example.Final_Project.models.Answer;
import com.example.Final_Project.models.Question;
import com.example.Final_Project.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question createQuestion(Question question){
        return questionRepository.save(question);
    }

    public Question deleteQuestion(String id){
        questionRepository.deleteById(id);
        return null;
    }

    public List<Question> getallQuestions(){
       return questionRepository.findAll();
    }


    public Question updateQuestion(Question question){
        return questionRepository.save(question);
    }

    public List<Answer> addAnswerToQuestion(String questionId, Answer answer){
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if(questionOptional.isPresent()) {
            Question question = questionOptional.get();
            question.getAnswers().add(answer);
            return questionRepository.save(question).getAnswers();
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "question not found");
        }

    }

    public List<Answer> deleteAnswerFromQuestion(String questionId, String answerId){
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if(questionOptional.isPresent()) {
            Question question = questionOptional.get();
            for(Answer answer : question.getAnswers()){
                if(answer.getId().equals(answerId)) {
                question.getAnswers().remove(answer);
                }
                }
           return questionRepository.save(question).getAnswers();

        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "question not found");
        }

    }

    public List<Answer> updateAnswerInQuestion(String questionId, String answerId, String content){
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if(questionOptional.isPresent()) {
            Question question = questionOptional.get();
            for(Answer answer : question.getAnswers()){
                if(answer.getId().equals(answerId)) {
                    answer.setContent(content);
                    return questionRepository.save(question).getAnswers();
                }
            }
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "question not found");
        }
    return null ;
    }

    public List<Answer> getAnswersForQuestion(String questionId,String sortBy){
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if(questionOptional.isPresent()){
            List<Answer> answers = questionOptional.get().getAnswers();

            return switch (sortBy.toLowerCase()) {
                case "latest" -> {
                    answers.sort(Comparator.comparing(Answer::getCreatedDate).reversed());
                    yield answers;
                }
                case "earliest" -> {
                    answers.sort(Comparator.comparing(Answer::getCreatedDate));
                    yield answers;
                }
                case "rating" -> {
                    answers.sort(Comparator.comparing(Answer::getRating).reversed());
                    yield answers;
                }
                default -> answers;
            };
        }
        return Collections.emptyList();
    }

public Answer rateAnswer(String questionId, String answerId, int rating){
    Optional<Question> questionOptional = questionRepository.findById(questionId);
    if(questionOptional.isPresent()){
        Question question = questionOptional.get();
        for(Answer answer : question.getAnswers()){
            if(answer.getId().equals(answerId)){
             float totalRating = answer.getRating() * answer.getRatingCount() + rating;
             answer.setRatingCount(answer.getRatingCount() + 1);
             answer.setRating(totalRating/ answer.getRatingCount());
             questionRepository.save(question);
             return answer;
            }
        }
    }
    return null;
}

    public Answer deleterateAnswer(String questionId, String answerId, int rating){
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if(questionOptional.isPresent()){
            Question question = questionOptional.get();
            for(Answer answer : question.getAnswers()){
                if(answer.getId().equals(answerId)){
                    float totalRating = answer.getRating() * answer.getRatingCount() - rating;
                    answer.setRatingCount(answer.getRatingCount() - 1);
                    answer.setRating(totalRating/ answer.getRatingCount());
                    questionRepository.save(question);
                    return answer;
                }
            }
        }
        return null;
    }

    public Answer updaterateAnswer(String questionId, String answerId, int oldrating, int newrating){
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if(questionOptional.isPresent()){
            Question question = questionOptional.get();
            for(Answer answer : question.getAnswers()){
                if(answer.getId().equals(answerId)){
                    float totalRating = answer.getRating() * answer.getRatingCount() - oldrating + newrating;
                    answer.setRating(totalRating/ answer.getRatingCount());
                    questionRepository.save(question);
                    return answer;
                }
            }
        }
        return null;
    }


    public Question getQuestionById(String id) {
      return  questionRepository.findById(id).get();

    }
}



