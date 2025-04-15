package edu.quangtk.thiGK.ntu64131937.Models;

public class Post {
    private Long id;
    private String title;
    private String content;
    private String categoryID;

    public Post(Long id, String title, String content, String categoryID) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.categoryID = categoryID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }
}
