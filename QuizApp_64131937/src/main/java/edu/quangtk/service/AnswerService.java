package edu.quangtk.service;

import edu.quangtk.model.Answer;
import edu.quangtk.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
  @Autowired
  private AnswerRepository answerRepository;

  public List<Answer> findAnswersByQuestionId(Long questionId) {
    return answerRepository.findByQuestionId(questionId);
  }

  public Optional<Answer> findAnswerById(Long id) {
    return answerRepository.findById(id);
  }

  public Answer saveAnswer(Answer answer) {
    return answerRepository.save(answer);
  }

  public void deleteAnswerById(Long id) {
    answerRepository.deleteById(id);
  }
}