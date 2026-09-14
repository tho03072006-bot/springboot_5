package edu.hcmute.webpr.entity;

import java.io.Serializable;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entity ánh xạ (map) tới bảng "tl_category" trong database.
 * Đặt tên bảng riêng (tl_category) để KHÔNG trùng với bảng "Category" của
 * project bt2-servlet-jsp hay bảng "categories" của project Baitap02 - mỗi
 * project của môn học đều có bảng dữ liệu độc lập của riêng nó.
 *
 * Không dùng Lombok (viết tay constructor/getter/setter/toString) để tránh
 * phải cài thêm Lombok plugin vào Eclipse.
 */
@Entity
@Table(name = "tl_category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @NotBlank(message = "Tên danh mục không được để trống")
    @Size(max = 100, message = "Tên danh mục tối đa 100 ký tự")
    @Nationalized
    @Column(name = "category_name", length = 100, nullable = false)
    private String name;

    @Size(max = 255, message = "Mô tả tối đa 255 ký tự")
    @Nationalized
    @Column(name = "description", length = 255)
    private String description;

    public Category() {
    }

    public Category(Long categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Category [categoryId=" + categoryId + ", name=" + name + ", description=" + description + "]";
    }

}
