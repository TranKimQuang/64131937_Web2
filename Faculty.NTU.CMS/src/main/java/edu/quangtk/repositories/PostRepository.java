package edu.quangtk.repositories;

import edu.quangtk.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByFacultyId(Long facultyId);
    List<Post> findByCategoryId(Long categoryId);
    Post findBySlug(String slug);
    List<Post> findByStatus(Post.Status status);
}