# Bài tập cá nhân - Spring Boot + Thymeleaf + Thymeleaf Layout Dialect

CRUD + tìm kiếm + phân trang cho **Category**, dùng Thymeleaf làm view và
Thymeleaf Layout Dialect để tái sử dụng bố cục header - content - footer.

## Cấu trúc chính

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

## Chạy project

1. Import vào Eclipse/STS: **File > Import > Maven > Existing Maven Projects** > chọn
   thư mục `springboot_5`.
2. **Tạo file mật khẩu riêng** (không nằm trong git): copy
   `application-secrets.properties.example` thành `application-secrets.properties`
   (cùng cấp với `pom.xml`) rồi điền mật khẩu SQL Server thật (`sa` trên
   `localhost,1433`, database `webst2`). File này đã nằm trong `.gitignore` nên sẽ
   không bao giờ bị push lên GitHub. Nếu Hibernate không tự tạo được bảng
   `tl_category`, chạy tay `database/tl_category.sql` trong SSMS.
3. Chuột phải project > **Run As > Spring Boot App** → mở `http://localhost:8081/`
   (hoặc dòng lệnh: `mvn package` rồi `java -jar target/springboot5.jar`).

## Đã hoàn thành theo đề bài

- **CRUD Category** (thêm/sửa/xóa/xem), **tìm kiếm** theo tên, **phân trang** - giữ
  nguyên từ khóa khi chuyển trang.
- **Thymeleaf + Thymeleaf Layout Dialect**: `layout/main-layout.html` chứa
  header/content/footer dùng chung; mỗi trang chỉ khai báo `layout:fragment="content"`.
- **Header**: ảnh cá nhân `static/images/tran-minh-tho.png`.
- **Footer**: Trần Minh Thọ - MSSV 24133059, môn WEBPR330479 - GVHD ThS. Nguyễn Hữu Trung.

## Ghi chú kỹ thuật

- Không dùng Lombok (viết tay getter/setter). Comment tiếng Việt, tên class/biến
  tiếng Anh theo chuẩn Java Naming Convention.
- Bản sửa 14/09/2026: fix lỗi 500 khi phân trang nhiều trang, giới hạn `size` 1-100,
  dùng `NVARCHAR` cho dữ liệu tiếng Việt. Build với JDK 21 (mã nguồn Java 17).
- Nếu Maven lỗi không tạo được repo tại `C:\.m2`, dùng:
  `mvn "-Dmaven.repo.local=%USERPROFILE%\.m2\repository" package` (Command Prompt).

⚠️ **Bảo mật**: mật khẩu SQL Server thật đã được chuyển ra file
`application-secrets.properties` (không commit lên git - xem bước 2 ở trên).
`application.properties` (file có commit lên GitHub) không còn chứa mật khẩu thật
nữa. Mật khẩu cũ từng bị lộ ở các commit trước (lúc repo còn để public) - nên đổi
lại mật khẩu SQL Server thật để chắc chắn an toàn.
