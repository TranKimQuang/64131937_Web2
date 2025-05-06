package edu.quangtk.repositories;

import edu.quangtk.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByFacultyId(Long facultyId);
    List<Menu> findByParentId(Long parentId);
}