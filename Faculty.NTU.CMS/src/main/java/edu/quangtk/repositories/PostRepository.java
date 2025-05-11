package edu.quangtk.repositories;

import edu.quangtk.entity.Post;
import edu.quangtk.entity.Post.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByFacultyId(Long facultyId);
    List<Post> findByFacultyIdAndStatus(Long facultyId, Status status);
    List<Post> findByStatus(Status status);
    Optional<Post> findBySlug(String slug);
}