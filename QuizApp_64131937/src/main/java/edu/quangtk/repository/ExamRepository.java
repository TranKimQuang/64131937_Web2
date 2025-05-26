package edu.quangtk.repository;

import edu.quangtk.model.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
  Page<Exam> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}