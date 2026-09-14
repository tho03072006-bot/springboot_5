package edu.hcmute.webpr.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.hcmute.webpr.entity.Category;

/**
 * Interface tầng Service - nơi đặt các logic nghiệp vụ (nếu có) trước khi
 * gọi xuống Repository. Đặt tên theo đúng quy ước "I + TênService" đã dùng
 * xuyên suốt các bài tập khác của môn (vd ICategoryService/CategoryServiceImpl
 * trong project Baitap02).
 */
public interface ICategoryService {

    Page<Category> findAll(Pageable pageable);

    Page<Category> findByNameContaining(String name, Pageable pageable);

    Optional<Category> findById(Long id);

    Category save(Category category);

    void deleteById(Long id);

}
