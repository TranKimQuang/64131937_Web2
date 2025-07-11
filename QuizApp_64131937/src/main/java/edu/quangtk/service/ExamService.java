package edu.quangtk.service;

import edu.quangtk.model.Exam;
import edu.quangtk.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamService {

  @Autowired
  private ExamRepository examRepository;

  public List<Exam> findAllExams() {
    return examRepository.findAll();
  }

  public Optional<Exam> findExamById(Long id) {
    return examRepository.findById(id);
  }

  public Exam saveExam(Exam exam) {
    return examRepository.save(exam);
  }

  public void deleteExamById(Long id) {
    examRepository.deleteById(id);
  }

  public Page<Exam> findExams(String searchTerm, Pageable pageable) {
    if (searchTerm != null && !searchTerm.trim().isEmpty()) {
      return examRepository.findByTitleContainingIgnoreCase(searchTerm, pageable);
    }
    return examRepository.findAll(pageable);
  }
  public List<Exam> searchExamsByTitle(String keyword) {
    return examRepository.findByTitleContainingIgnoreCase(keyword);
  }
}