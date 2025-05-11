package edu.quangtk.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "menus")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String url;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Menu parent;

    @Column(name = "display_order")
    private int displayOrder;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructors
    public Menu() {
    }

    public Menu(String name, String url, Menu parent, int displayOrder, Faculty faculty) {
        this.name = name;
        this.url = url;
        this.parent = parent;
        this.displayOrder = displayOrder;
        this.faculty = faculty;
    }

    // Lifecycle callback
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long menuId) {
        this.id = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu)) return false;
        Menu menu = (Menu) o;
        return id != null && id.equals(menu.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // toString()
    @Override
    public String toString() {
        return "Menu{" +
                "menuId=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", displayOrder=" + displayOrder +
                '}';
    }
}