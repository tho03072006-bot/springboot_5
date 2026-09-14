# Bài tập cá nhân - Spring Boot + Thymeleaf + Thymeleaf Layout Dialect

```
springboot_5/
├── pom.xml
├── database/tl_category.sql              <- script tạo bảng thủ công (nếu Hibernate không tự tạo)
└── src/main/
    ├── java/edu/hcmute/webpr/
    │   ├── Springboot5Application.java   <- class main
    │   ├── entity/Category.java
    │   ├── repository/CategoryRepository.java
    │   ├── service/(ICategoryService, impl/CategoryServiceImpl)
    │   └── controller/(CategoryController, HomeController)
    └── resources/
        ├── application.properties
        ├── static/{css/style.css, images/tran-minh-tho.png}
        └── templates/
            ├── layout/main-layout.html   <- layout header/content/footer
            ├── fragments/(header.html, footer.html)
            └── category/(list.html, form.html)
```
