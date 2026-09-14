package edu.hcmute.webpr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.hcmute.webpr.entity.Category;

/**
 * Tầng Repository - Spring Data JPA sẽ tự động sinh code triển khai (implementation)
 * cho interface này lúc chạy (không cần viết tay như JDBC thuần).
 *
 * JpaRepository<Category, Long> đã có sẵn các hàm CRUD cơ bản: save, findById,
 * findAll, deleteById... Ta chỉ cần khai báo thêm hàm tìm kiếm theo tên.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Tìm kiếm Category theo tên (không phân biệt hoa/thường, tìm gần đúng - LIKE %name%)
     * có phân trang. Spring Data JPA tự sinh câu lệnh dựa theo tên hàm (query method).
     */
    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
