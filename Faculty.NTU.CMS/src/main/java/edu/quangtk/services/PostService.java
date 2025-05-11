package edu.quangtk.services;

import edu.quangtk.entity.Post;
import edu.quangtk.entity.Post.Status;
import edu.quangtk.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPostsByFaculty(Long facultyId) {
        return (facultyId != null) ? postRepository.findByFacultyId(facultyId) : postRepository.findAll();
    }

    public Post createPost(Post post) {
        post.setStatus(Status.DRAFT); // Mặc định là DRAFT
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post post) {
        Post existing = postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        existing.setTitle(post.getTitle());
        existing.setSlug(post.getSlug());
        existing.setContent(post.getContent());
        existing.setStatus(post.getStatus());
        existing.setCategory(post.getCategory());
        existing.setApprovedBy(post.getApprovedBy());
        return postRepository.save(existing);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
    }

    public Post getPostBySlug(String slug) {
        return postRepository.findBySlug(slug)
            .orElseThrow(() -> new RuntimeException("Post not found with slug: " + slug));
    }

    public List<Post> getPendingPosts(Long facultyId) {
        return (facultyId != null) ? postRepository.findByFacultyIdAndStatus(facultyId, Status.PENDING)
                : postRepository.findByStatus(Status.PENDING);
    }

    public Post approvePost(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        post.setStatus(Status.PUBLISHED);
        return postRepository.save(post);
    }
}