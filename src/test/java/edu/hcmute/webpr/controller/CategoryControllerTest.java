package edu.hcmute.webpr.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import edu.hcmute.webpr.entity.Category;
import edu.hcmute.webpr.service.ICategoryService;

@WebMvcTest({CategoryController.class, HomeController.class})
class CategoryControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private ICategoryService service;

    @Test
    void invalidPageSizesStillRenderTheList() throws Exception {
        when(service.findAll(any(Pageable.class))).thenAnswer(invocation ->
                new PageImpl<>(List.of(new Category(1L, "Đồ uống", "Trà")),
                        invocation.getArgument(0), 1));
        for (String size : List.of("0", "-1", "1000000")) {
            mvc.perform(get("/categories").param("size", size))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Đồ uống")))
                    .andExpect(content().string(containsString("Trần Minh Thọ")));
        }
    }

    @Test
    void outOfRangeSearchReturnsLastPageAndKeepsKeyword() throws Exception {
        when(service.findByNameContaining(eq("Trà"), any(Pageable.class)))
                .thenAnswer(invocation -> {
                    Pageable page = invocation.getArgument(1);
                    return new PageImpl<>(page.getPageNumber() > 1 ? List.of()
                            : List.of(new Category(1L, "Trà xanh", "")), page, 6);
                });
        mvc.perform(get("/categories").param("keyword", "  Trà  ").param("page", "999"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("keyword", "Trà"))
                .andExpect(content().string(containsString("Trà xanh")))
                .andExpect(content().string(containsString("pagination__link--active")));
    }

    @Test
    void emptySearchAtHighPageRendersNormally() throws Exception {
        when(service.findAll(any(Pageable.class))).thenAnswer(invocation ->
                new PageImpl<>(List.of(), invocation.getArgument(0), 0));
        mvc.perform(get("/categories").param("page", "999"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Chưa có danh mục nào")));
    }

    @Test
    void blankNameRedisplaysValidationMessage() throws Exception {
        mvc.perform(post("/categories/save").param("name", " "))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("category", "name"))
                .andExpect(content().string(containsString("Tên danh mục không được để trống")));
        verify(service, never()).save(any());
    }

    @Test
    void savingDeletedCategoryRedirectsWithoutRecreatingIt() throws Exception {
        when(service.findById(99L)).thenReturn(Optional.empty());
        mvc.perform(post("/categories/save").param("categoryId", "99").param("name", "Trà"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/categories"))
                .andExpect(flash().attribute("message", "Danh mục đã bị xóa hoặc không tồn tại!"));
        verify(service, never()).save(any());
    }

    @Test
    void homeAndAddFormAreAvailable() throws Exception {
        mvc.perform(get("/")).andExpect(redirectedUrl("/categories"));
        mvc.perform(get("/categories/add"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Thêm danh mục mới")));
    }
}
