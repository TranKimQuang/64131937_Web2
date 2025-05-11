package edu.quangtk.services;

import edu.quangtk.entity.Menu;
import edu.quangtk.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public List<Menu> getMenusByFaculty(Long facultyId) {
        return menuRepository.findByFacultyId(facultyId);
    }

    public Menu getMenuById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found with id: " + id));
    }

    public List<Menu> getSubMenus(Long parentId) {
        return menuRepository.findByParentId(parentId);
    }

    public Menu updateMenu(Long id, Menu updatedMenu) {
        Menu menu = getMenuById(id);
        menu.setName(updatedMenu.getName());
        menu.setUrl(updatedMenu.getUrl());
        menu.setParent(updatedMenu.getParent());
        menu.setDisplayOrder(updatedMenu.getDisplayOrder());
        menu.setFaculty(updatedMenu.getFaculty());
        return menuRepository.save(menu);
    }

    public void deleteMenu(Long id) {
        Menu menu = getMenuById(id);
        menuRepository.delete(menu);
    }
}