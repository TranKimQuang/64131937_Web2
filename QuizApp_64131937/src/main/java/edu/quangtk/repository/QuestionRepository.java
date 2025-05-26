package edu.quangtk.repository;

import edu.quangtk.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
  List<Question> findByExamId(Long examId);
  Page<Question> findByExamId(Long examId, Pageable pageable);
  Page<Question> findByExamIdAndContentContainingIgnoreCase(Long examId, String content, Pageable pageable);
}