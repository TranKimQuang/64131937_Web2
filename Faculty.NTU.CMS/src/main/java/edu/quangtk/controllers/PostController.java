package edu.quangtk.controllers;

import com.ntu.facultycms.entity.Post;
import com.ntu.facultycms.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty/{facultyId}/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Post> createPost(@PathVariable Long facultyId, @RequestBody Post post) {
        post.setFacultyId(facultyId);
        return ResponseEntity.ok(postService.createPost(post));
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<List<Post>> getAllPosts(@PathVariable Long facultyId) {
        return ResponseEntity.ok(postService.getAllPostsByFaculty(facultyId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Post> getPostById(@PathVariable Long facultyId, @PathVariable Long id) {
        Post post = postService.getPostById(id);
        if (!post.getFacultyId().equals(facultyId)) {
            throw new RuntimeException("Post does not belong to this faculty");
        }
        return ResponseEntity.ok(post);
    }

    @GetMapping("/slug/{slug}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Post> getPostBySlug(@PathVariable Long facultyId, @PathVariable String slug) {
        Post post = postService.getPostBySlug(slug);
        if (!post.getFacultyId().equals(facultyId)) {
            throw new RuntimeException("Post does not belong to this faculty");
        }
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Post> updatePost(@PathVariable Long facultyId, @PathVariable Long id, @RequestBody Post post) {
        post.setFacultyId(facultyId);
        return ResponseEntity.ok(postService.updatePost(id, post));
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('FACULTY_ADMIN')")
    public ResponseEntity<Post> approvePost(@PathVariable Long facultyId, @PathVariable Long id) {
        Post post = postService.getPostById(id);
        if (!post.getFacultyId().equals(facultyId)) {
            throw new RuntimeException("Post does not belong to this faculty");
        }
        return ResponseEntity.ok(postService.approvePost(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN')")
    public ResponseEntity<Void> deletePost(@PathVariable Long facultyId, @PathVariable Long id) {
        Post post = postService.getPostById(id);
        if (!post.getFacultyId().equals(facultyId)) {
            throw new RuntimeException("Post does not belong to this faculty");
        }
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}