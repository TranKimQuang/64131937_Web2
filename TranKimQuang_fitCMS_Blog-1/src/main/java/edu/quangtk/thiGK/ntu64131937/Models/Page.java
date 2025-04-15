package edu.quangtk.thiGK.ntu64131937.Models;

public class Page {
    private Long id;
    private String pageName;
    private String keyword;
    private String content;
    private Long parentPageID;
 
    public Page(Long id, String pageName, String keyword, String content, Long parentPageID) {
        this.id = id;
        this.pageName = pageName;
        this.keyword = keyword;
        this.content = content;
        this.parentPageID = parentPageID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getParentPageID() {
        return parentPageID;
    }

    public void setParentPageID(Long parentPageID) {
        this.parentPageID = parentPageID;
    }
}
