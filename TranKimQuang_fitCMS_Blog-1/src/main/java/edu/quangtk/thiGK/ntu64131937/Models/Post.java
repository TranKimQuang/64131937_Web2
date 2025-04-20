package edu.quangtk.thiGK.ntu64131937.Models;

public class Post {
    private String id;
    private String title;
    private String content;
    private String categoryID;

    public Post(String id, String title, String content, String categoryID) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.categoryID = categoryID;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
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