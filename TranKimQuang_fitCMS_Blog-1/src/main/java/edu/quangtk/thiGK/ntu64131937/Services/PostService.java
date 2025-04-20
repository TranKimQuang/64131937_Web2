package edu.quangtk.thiGK.ntu64131937.Services;

import edu.quangtk.thiGK.ntu64131937.Models.Post;
import edu.quangtk.thiGK.ntu64131937.Repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // Thêm bài viết mới
    public void addPost(String title, String content, String categoryID) {
        Post post = new Post(null, title, content, categoryID);
        postRepository.save(post);
    }

    // Lấy tất cả bài viết
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Lấy bài viết theo ID
    public Post getPostById(Long id) {
        return postRepository.findById(id);
    }

    // Cập nhật bài viết
    public boolean updatePost(Long id, String title, String content, String categoryID) {
        Post post = new Post(id, title, content, categoryID);
        return postRepository.update(post);
    }

    // Xóa bài viết
    public boolean deletePost(Long id) {
        return postRepository.delete(id);
    }

    // Lấy bài viết theo danh mục
    public List<Post> getPostsByCategory(String categoryID) {
        return postRepository.findByCategoryId(categoryID);
    }
}