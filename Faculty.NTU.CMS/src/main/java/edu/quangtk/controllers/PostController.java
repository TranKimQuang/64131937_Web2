package edu.quangtk.controllers;

import edu.quangtk.entity.*;
import edu.quangtk.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/faculty/{facultyId}/posts")
public class PostController {
    private final PostService postService;
    private final FacultyService facultyService;
    private final UserService userService;
    private final CategoryService categoryService;

    public PostController(PostService postService,
                         FacultyService facultyService,
                         UserService userService,
                         CategoryService categoryService) {
        this.postService = postService;
        this.facultyService = facultyService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Post> createPost(
            @PathVariable Long facultyId,
            @RequestBody Post post,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Faculty faculty = facultyService.getFacultyById(facultyId);
            User creator = userService.getUserByUsername(userDetails.getUsername());

            post.setFaculty(faculty);
            post.setCreatedBy(creator);
            post.setCreatedAt(LocalDateTime.now());
            post.setStatus(Post.Status.PENDING); // Sử dụng enum đúng từ Post.java

            Post createdPost = postService.createPost(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<List<Post>> getAllPosts(@PathVariable Long facultyId) {
        try {
            List<Post> posts = postService.getAllPostsByFaculty(facultyId);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Post> getPostById(
            @PathVariable Long facultyId,
            @PathVariable Long id) {
        try {
            Post post = postService.getPostById(id);
            if (!post.getFaculty().getId().equals(facultyId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/slug/{slug}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Post> getPostBySlug(
            @PathVariable Long facultyId,
            @PathVariable String slug) {
        try {
            Post post = postService.getPostBySlug(slug);
            if (!post.getFaculty().getId().equals(facultyId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN') or hasRole('EDITOR')")
    public ResponseEntity<Post> updatePost(
            @PathVariable Long facultyId,
            @PathVariable Long id,
            @RequestBody Post postDetails,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Post existingPost = postService.getPostById(id);
            if (!existingPost.getFaculty().getId().equals(facultyId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            existingPost.setTitle(postDetails.getTitle());
            existingPost.setSlug(postDetails.getSlug());
            existingPost.setContent(postDetails.getContent());

            if (postDetails.getCategory() != null) {
                Category category = categoryService.getCategoryById(postDetails.getCategory().getId());
                existingPost.setCategory(category);
            }

            // Chỉ Editor hoặc Faculty Admin có thể đặt lại thành PENDING
            if (existingPost.getStatus() == Post.Status.PUBLISHED) {
                existingPost.setStatus(Post.Status.PENDING);
                existingPost.setApprovedBy(null);
            } else {
                existingPost.setStatus(postDetails.getStatus());
            }

            Post updatedPost = postService.updatePost(id, existingPost);
            return ResponseEntity.ok(updatedPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('FACULTY_ADMIN')")
    public ResponseEntity<Post> approvePost(
            @PathVariable Long facultyId,
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Post post = postService.getPostById(id);
            if (!post.getFaculty().getId().equals(facultyId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            User approver = userService.getUserByUsername(userDetails.getUsername());
            post.setApprovedBy(approver);
            post.setStatus(Post.Status.PUBLISHED);

            Post approvedPost = postService.updatePost(id, post);
            return ResponseEntity.ok(approvedPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN')")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long facultyId,
            @PathVariable Long id) {
        try {
            Post post = postService.getPostById(id);
            if (!post.getFaculty().getId().equals(facultyId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}