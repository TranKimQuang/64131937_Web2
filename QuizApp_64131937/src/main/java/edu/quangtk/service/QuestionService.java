package edu.quangtk.service;

import edu.quangtk.model.Answer;
import edu.quangtk.model.Question;
import edu.quangtk.repository.AnswerRepository;
import edu.quangtk.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

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
            // Xóa các đáp án cũ nếu có trước khi lưu mới (cho trường hợp chỉnh sửa)
            if (question.getId() != null) {
                answerRepository.deleteAll(answerRepository.findByQuestionId(savedQuestion.getId()));
            }
            answerRepository.saveAll(question.getAnswers());
        }
        return savedQuestion;
    }

    public void deleteQuestionById(Long id) {
        questionRepository.deleteById(id);
    }


    public Page<Question> findQuestionsByExamId(Long examId, String searchTerm, Pageable pageable) {
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            return questionRepository.findByExamIdAndContentContainingIgnoreCase(examId, searchTerm, pageable);
        }
        return questionRepository.findByExamId(examId, pageable);
    }
}