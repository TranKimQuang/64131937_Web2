package edu.quangtk.thiGK.ntu64131937.Repositories;

import edu.quangtk.thiGK.ntu64131937.Models.Page;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PageRepository {
    private List<Page> pages = new ArrayList<>();
    private Long nextId = 1L;

    // Thêm trang mới
    public void save(Page page) {
        if (page.getId() == null) {
            page.setId(nextId++);
        }
        pages.add(page);
    }

    // Lấy tất cả trang
    public List<Page> findAll() {
        return new ArrayList<>(pages);
    }

    // Tìm trang theo ID
    public Page findById(Long id) {
        return pages.stream()
                .filter(page -> page.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Cập nhật trang
    public boolean update(Page page) {
        Page existingPage = findById(page.getId());
        if (existingPage != null) {
            existingPage.setPageName(page.getPageName());
            existingPage.setKeyword(page.getKeyword());
            existingPage.setContent(page.getContent());
            existingPage.setParentPageID(page.getParentPageID());
            return true;
        }
        return false;
    }

    // Xóa trang
    public boolean delete(Long id) {
        return pages.removeIf(page -> page.getId().equals(id));
    }

    // Tìm các trang con theo ID trang cha
    public List<Page> findByParentPageId(Long parentPageID) {
        return pages.stream()
                .filter(page -> parentPageID.equals(page.getParentPageID()))
                .collect(Collectors.toList());
    }
}