package edu.quangtk.thiGK.ntu64131937.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "pages")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "page_name", nullable = false)
    private String pageName;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "parent_page_id")
    private Long parentPageID;

    // Constructors
    public Page() {}

    public Page(String pageName, String keyword, String content, Long parentPageID) {
        this.pageName = pageName;
        this.keyword = keyword;
        this.content = content;
        this.parentPageID = parentPageID;
    }

    // Getters and Setters
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