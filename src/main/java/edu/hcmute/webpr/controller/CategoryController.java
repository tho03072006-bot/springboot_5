package edu.hcmute.webpr.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.hcmute.webpr.entity.Category;
import edu.hcmute.webpr.service.ICategoryService;
import jakarta.validation.Valid;

/**
 * Controller nhận request từ người dùng cho chức năng Category:
 *  - GET  /categories          : danh sách + tìm kiếm (keyword) + phân trang (page, size)
 *  - GET  /categories/add      : hiển thị form thêm mới
 *  - GET  /categories/edit/{id}: hiển thị form sửa
 *  - POST /categories/save     : lưu (thêm mới hoặc cập nhật, phân biệt bằng categoryId có null hay không)
 *  - GET  /categories/delete/{id} : xóa
 *
 * View (Thymeleaf) đặt tại src/main/resources/templates/category/*.html,
 * dùng chung layout (header - content - footer) qua Thymeleaf Layout Dialect
 * ở templates/layout/main-layout.html.
 */
@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping({"", "/"})
    public String list(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            Model model) {

        // Sắp xếp theo categoryId giảm dần để danh mục mới thêm hiện lên đầu
        size = Math.max(1, Math.min(size, 100));
        keyword = keyword == null ? null : keyword.trim();
        Pageable pageable = PageRequest.of(Math.max(page, 0), size, Sort.by("categoryId").descending());

        Page<Category> categoryPage;
        if (StringUtils.hasText(keyword)) {
            categoryPage = categoryService.findByNameContaining(keyword.trim(), pageable);
        } else {
            categoryPage = categoryService.findAll(pageable);
        }

        // Tính danh sách số trang hiển thị dạng "cửa sổ trượt" quanh trang hiện tại
        // (giống ví dụ trong slide bài giảng Thymeleaf), tối đa 5 số trang mỗi lần
        int totalPages = categoryPage.getTotalPages();
        // URL cũ hoặc dữ liệu vừa bị xóa có thể làm số trang vượt giới hạn.
        int lastPage = Math.max(0, totalPages - 1);
        if (categoryPage.getNumber() > lastPage) {
            pageable = PageRequest.of(lastPage, size, pageable.getSort());
            categoryPage = StringUtils.hasText(keyword)
                    ? categoryService.findByNameContaining(keyword, pageable)
                    : categoryService.findAll(pageable);
        }
        if (totalPages > 0) {
            int currentPage = categoryPage.getNumber();
            int start = Math.max(0, currentPage - 2);
            int end = Math.min(totalPages - 1, currentPage + 2);
            List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("categoryPage", categoryPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);
        return "category/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        return categoryService.findById(id)
                .map(category -> {
                    model.addAttribute("category", category);
                    return "category/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("message", "Không tìm thấy danh mục cần sửa!");
                    return "redirect:/categories";
                });
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("category") Category category,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        // Nếu validate lỗi (vd bỏ trống tên) thì quay lại form, giữ nguyên dữ liệu đã nhập
        if (bindingResult.hasErrors()) {
            return "category/form";
        }

        boolean isEdit = category.getCategoryId() != null;
        if (isEdit && categoryService.findById(category.getCategoryId()).isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Danh mục đã bị xóa hoặc không tồn tại!");
            return "redirect:/categories";
        }
        category.setName(category.getName().trim());
        categoryService.save(category);

        redirectAttributes.addFlashAttribute("message",
                isEdit ? "Cập nhật danh mục thành công!" : "Thêm danh mục mới thành công!");
        // redirect (Post/Redirect/Get) để tránh submit lại form khi người dùng bấm F5
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        categoryService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Xóa danh mục thành công!");
        return "redirect:/categories";
    }

}
