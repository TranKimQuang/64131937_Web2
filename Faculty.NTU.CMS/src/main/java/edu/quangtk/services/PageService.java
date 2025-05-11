package edu.quangtk.services;

import edu.quangtk.entity.Page;
import edu.quangtk.repositories.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageService {
    @Autowired
    private PageRepository pageRepository;

    public Page createPage(Page page) {
        return pageRepository.save(page);
    }

    public List<Page> getAllPagesByFaculty(Long facultyId) {
        return pageRepository.findByFacultyId(facultyId);
    }

    public Page getPageById(Long id) {
        return pageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Page not found with id: " + id));
    }

    public Page getPageBySlug(String slug) {
        return pageRepository.findBySlug(slug);
    }

    public Page updatePage(Long id, Page updatedPage) {
        Page page = getPageById(id);
        page.setTitle(updatedPage.getTitle());
        page.setSlug(updatedPage.getSlug());
        page.setContent(updatedPage.getContent());
        page.setFaculty(updatedPage.getFaculty());
        page.setCreatedBy(updatedPage.getCreatedBy());
        return pageRepository.save(page);
    }

    public void deletePage(Long id) {
        Page page = getPageById(id);
        pageRepository.delete(page);
    }
}