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
    @PreAuthorize("hasRole('FACULTY_ADMIN')")
    public ResponseEntity<?> createPost(
            @PathVariable Long facultyId,
            @RequestBody Post post,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Faculty faculty = facultyService.getFacultyById(facultyId);
            User creator = userService.getUserByUsername(userDetails.getUsername());

            // Kiểm tra quyền truy cập
            if (!creator.getFaculty().getId().equals(facultyId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You do not have permission to create posts in this faculty.");
            }

            post.setFaculty(faculty);
            post.setCreatedBy(creator);
            post.setCreatedAt(LocalDateTime.now());
            post.setUpdatedAt(LocalDateTime.now());
            // Không cần set status vì bài viết được đăng ngay lập tức

            Post createdPost = postService.createPost(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create post: " + e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN')")
    public ResponseEntity<?> getAllPosts(@PathVariable Long facultyId) {
        try {
            List<Post> posts = postService.getAllPostsByFaculty(facultyId);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve posts: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN')")
    public ResponseEntity<?> getPostById(
            @PathVariable Long facultyId,
            @PathVariable Long id) {
        try {
            Post post = postService.getPostById(id);
            if (!post.getFaculty().getId().equals(facultyId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You do not have permission to view this post.");
            }
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Post not found: " + e.getMessage());
        }
    }

    @GetMapping("/slug/{slug}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('FACULTY_ADMIN')")
    public ResponseEntity<?> getPostBySlug(
            @PathVariable Long facultyId,
            @PathVariable String slug) {
        try {
            Post post = postService.getPostBySlug(slug);
            if (!post.getFaculty().getId().equals(facultyId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You do not have permission to view this post.");
            }
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Post not found: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN')")
    public ResponseEntity<?> updatePost(
            @PathVariable Long facultyId,
            @PathVariable Long id,
            @RequestBody Post postDetails,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Post existingPost = postService.getPostById(id);
            if (!existingPost.getFaculty().getId().equals(facultyId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You do not have permission to update this post.");
            }

            existingPost.setTitle(postDetails.getTitle());
            existingPost.setSlug(postDetails.getSlug());
            existingPost.setContent(postDetails.getContent());

            if (postDetails.getCategory() != null) {
                Category category = categoryService.getCategoryById(postDetails.getCategory().getId());
                existingPost.setCategory(category);
            }

            existingPost.setUpdatedAt(LocalDateTime.now());
           
            Post updatedPost = postService.updatePost(id, existingPost);
            return ResponseEntity.ok(updatedPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update post: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FACULTY_ADMIN')")
    public ResponseEntity<?> deletePost(
            @PathVariable Long facultyId,
            @PathVariable Long id) {
        try {
            Post post = postService.getPostById(id);
            if (!post.getFaculty().getId().equals(facultyId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You do not have permission to delete this post.");
            }
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Failed to delete post: " + e.getMessage());
        }
    }
}