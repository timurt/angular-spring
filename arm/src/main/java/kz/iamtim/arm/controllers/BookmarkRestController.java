package kz.iamtim.arm.controllers;

import kz.iamtim.arm.dao.Bookmark;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Documentation for {@code BookmarkRestController}.
 *
 * @author Timur Tibeyev.
 */
@RestController
@RequestMapping("/bookmarks")
public class BookmarkRestController {
    @GetMapping
    Collection<Bookmark> readBookmarks() {
        List<Bookmark> results = new ArrayList<>();
        Bookmark a = new Bookmark();
        a.setAuthor("QWER");
        a.setDescription("ASDASDASD");
        results.add(a);
        return results;
    }
}
