package edu.quangtk.thiGK.ntu64131937.Services;

import edu.quangtk.thiGK.ntu64131937.Models.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PageService {
    private static final List<Page> pageList = new ArrayList<>();
    private static final AtomicLong idCounter = new AtomicLong(1);

    public PageService() {
        addPage("Trang chủ", "home", "Nội dung trang chủ", null);
        addPage("Giới thiệu", "about", "Giới thiệu về chúng tôi", "1");
        addPage("Liên hệ", "contact", "Thông tin liên hệ", "1");
    }

    public void addPage(String pageName, String keyword, String content, String parentPageID) {
        String id = String.valueOf(idCounter.getAndIncrement());
        Page page = new Page(id, pageName, keyword, content, parentPageID);
        pageList.add(page);
    }

    public List<Page> getAllPages() {
        return new ArrayList<>(pageList);
    }

    public Page getPageById(String id) {
        return pageList.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean updatePage(String id, String pageName, String keyword, String content, String parentPageID) {
        for (Page page : pageList) {
            if (page.getId().equals(id)) {
                page.setPageName(pageName);
                page.setKeyword(keyword);
                page.setContent(content);
                page.setParentPageID(parentPageID);
                return true;
            }
        }
        return false;
    }

    public boolean deletePage(String id) {
        return pageList.removeIf(p -> p.getId().equals(id));
    }

    public List<Page> getChildPages(String parentPageID) {
        List<Page> result = new ArrayList<>();
        for (Page page : pageList) {
            if (parentPageID != null && parentPageID.equals(page.getParentPageID())) {
                result.add(page);
            }
        }
        return result;
    }
}