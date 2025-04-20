package edu.quangtk.thiGK.ntu64131937.Repositories;

import edu.quangtk.thiGK.ntu64131937.Models.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PostRepository {
    private List<Post> posts = new ArrayList<>();
    private Long nextId = 1L;

    // Thêm bài viết mới
    public void save(Post post) {
        if (post.getId() == null) {
            post.setId(nextId++);
        }
        posts.add(post);
    }

    // Lấy tất cả bài viết
    public List<Post> findAll() {
        return new ArrayList<>(posts);
    }

    // Tìm bài viết theo ID
    public Post findById(Long id) {
        return posts.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Cập nhật bài viết
    public boolean update(Post post) {
        Post existingPost = findById(post.getId());
        if (existingPost != null) {
            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());
            existingPost.setCategoryID(post.getCategoryID());
            return true;
        }
        return false;
    }

    // Xóa bài viết
    public boolean delete(Long id) {
        return posts.removeIf(post -> post.getId().equals(id));
    }

    // Tìm bài viết theo danh mục
    public List<Post> findByCategoryId(String categoryID) {
        return posts.stream()
                .filter(post -> categoryID.equals(post.getCategoryID()))
                .collect(Collectors.toList());
    }
}