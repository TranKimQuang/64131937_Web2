package edu.quangtk.thiGK.ntu64131937.Services;

import edu.quangtk.thiGK.ntu64131937.Models.Post;
import edu.quangtk.thiGK.ntu64131937.Repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PostService {
    private static final List<Post> postList = new ArrayList<>();
    private static final AtomicLong idCounter = new AtomicLong(1);

    public PostService() {
        // HARD CODE: Dữ liệu mẫu ban đầu
        addPost("Bài viết giới thiệu", "Đây là bài viết giới thiệu.", "cat1");
        addPost("Tin tức 1", "Nội dung tin tức số 1.", "cat2");
        addPost("Tin tức 2", "Nội dung tin tức số 2.", "cat2");
    }

    public void addPost(String title, String content, String categoryID) {
        Long id = idCounter.getAndIncrement();
        Post post = new Post(id, title, content, categoryID);
        postList.add(post);
    }

    public List<Post> getAllPosts() {
        return new ArrayList<>(postList);
    }

    public Post getPostById(Long id) {
        return postList.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean updatePost(Long id, String title, String content, String categoryID) {
        for (Post post : postList) {
            if (post.getId().equals(id)) {
                post.setTitle(title);
                post.setContent(content);
                post.setCategoryID(categoryID);
                return true;
            }
        }
        return false;
    }

    public boolean deletePost(Long id) {
        return postList.removeIf(p -> p.getId().equals(id));
    }

    public List<Post> getPostsByCategory(String categoryID) {
        List<Post> result = new ArrayList<>();
        for (Post post : postList) {
            if (post.getCategoryID().equalsIgnoreCase(categoryID)) {
                result.add(post);
            }
        }
        return result;
    }
}
