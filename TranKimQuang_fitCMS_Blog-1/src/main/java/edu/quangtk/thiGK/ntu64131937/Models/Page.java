package edu.quangtk.thiGK.ntu64131937.Models;

public class Page {
    private String id;
    private String pageName;
    private String keyword;
    private String content;
    private String parentPageID;

    public Page(String id, String pageName, String keyword, String content, String parentPageID) {
        this.id = id;
        this.pageName = pageName;
        this.keyword = keyword;
        this.content = content;
        this.parentPageID = parentPageID;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParentPageID() {
        return parentPageID;
    }

    public void setParentPageID(String parentPageID) {
        this.parentPageID = parentPageID;
    }
}