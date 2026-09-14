package edu.hcmute.webpr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller cho trang chủ ("/") - chuyển hướng luôn sang danh sách Category
 * vì bài tập chỉ xoay quanh chức năng CRUD + tìm kiếm + phân trang Category.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/categories";
    }

}
