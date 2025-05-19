package edu.quangtk.services;

import edu.quangtk.entity.Post;
import edu.quangtk.entity.User;
import edu.quangtk.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(Post post) {
        if (!"FACULTY_ADMIN".equals(post.getCreatedBy().getRole())) {
            throw new IllegalArgumentException("Only FACULTY_ADMIN can create posts");
        }
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post post) {
        Post existing = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        existing.setTitle(post.getTitle());
        existing.setSlug(post.getSlug());
        existing.setContent(post.getContent());
        existing.setCategory(post.getCategory());
        existing.setFaculty(post.getFaculty());
        existing.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(existing);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public List<Post> getAllPostsByFaculty(Long facultyId) {
        return postRepository.findByFacultyId(facultyId);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
    }

    public Post getPostBySlug(String slug) {
        return postRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
    }
}