package edu.hcmute.webpr.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.hcmute.webpr.entity.Category;
import edu.hcmute.webpr.repository.CategoryRepository;
import edu.hcmute.webpr.service.ICategoryService;

/**
 * Cài đặt (implementation) của ICategoryService.
 * Hiện tại chỉ ủy quyền (delegate) thẳng xuống CategoryRepository vì bài
 * tập chưa có quy tắc nghiệp vụ phức tạp - nếu sau này cần thêm quy tắc
 * (vd tự tính toán 1 field nào đó trước khi lưu) thì nơi đặt code là đây,
 * không đặt trong Controller.
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Page<Category> findByNameContaining(String name, Pageable pageable) {
        return categoryRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

}
