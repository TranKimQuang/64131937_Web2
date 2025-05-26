package edu.quangtk.service;

import edu.quangtk.model.Answer;
import edu.quangtk.model.Question;
import edu.quangtk.repository.AnswerRepository;
import edu.quangtk.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository; // Để lưu các đáp án con

    public List<Question> findAllQuestions() {
        return questionRepository.findAll();
    }

    public Optional<Question> findQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    public List<Question> findQuestionsByExamId(Long examId) {
        return questionRepository.findByExamId(examId);
    }

    public Question saveQuestion(Question question) {
        // Lưu câu hỏi trước để có ID
        Question savedQuestion = questionRepository.save(question);

        // Gán lại question cho từng answer và lưu answers
        if (question.getAnswers() != null) {
            for (Answer answer : question.getAnswers()) {
                answer.setQuestion(savedQuestion);
            }
            answerRepository.saveAll(question.getAnswers());
        }
        return savedQuestion;
    }

    public void deleteQuestionById(Long id) {
        questionRepository.deleteById(id);
    }
}