# Bài tập cá nhân - Spring Boot + Thymeleaf + Thymeleaf Layout Dialect

CRUD + tìm kiếm + phân trang cho chức năng **Category**, dùng Thymeleaf làm view
và Thymeleaf Layout Dialect để tái sử dụng bố cục header - content - footer.

## 1. Cấu trúc project

```
springboot_5/
├── pom.xml
├── database/tl_category.sql          <- script tạo bảng thủ công (nếu Hibernate không tự tạo)
└── src/main/
    ├── java/edu/hcmute/webpr/
    │   ├── Springboot5Application.java     <- class main, chạy để start server
    │   ├── entity/Category.java
    │   ├── repository/CategoryRepository.java
    │   ├── service/ICategoryService.java
    │   ├── service/impl/CategoryServiceImpl.java
    │   └── controller/
    │       ├── CategoryController.java     <- CRUD + search + pagination
    │       └── HomeController.java         <- redirect "/" -> "/categories"
    └── resources/
        ├── application.properties
        ├── static/css/style.css
        └── templates/
            ├── layout/main-layout.html     <- layout dùng chung (header/content/footer)
            ├── fragments/header.html       <- fragment header (ảnh cá nhân đã bổ sung)
            ├── fragments/footer.html       <- fragment footer (thông tin sinh viên)
            └── category/
                ├── list.html               <- danh sách + tìm kiếm + phân trang
                └── form.html                <- form thêm/sửa
```

## 2. Trước khi chạy - việc cần kiểm tra

1. **Mật khẩu SQL Server**: đã điền sẵn trong `src/main/resources/application.properties`
   (`spring.datasource.password`). Nếu đổi mật khẩu SQL Server sau này thì nhớ cập nhật
   lại dòng này. Lưu ý: KHÔNG nên push file này (có mật khẩu thật) lên GitHub public -
   xem ghi chú ngay trong file `application.properties`.
2. **Database**: mặc định trỏ vào database `webst2` (đã có sẵn từ các bài tập khác),
   nhưng dùng bảng RIÊNG tên `tl_category` nên không đụng dữ liệu các bài tập kia.
   `spring.jpa.hibernate.ddl-auto=update` sẽ cố tự tạo bảng khi chạy lần đầu; nếu
   không tự tạo được (đã từng gặp ở các bài trước), hãy tự chạy tay file
   `database/tl_category.sql` trong SSMS (script này có sẵn 8 dòng dữ liệu mẫu để
   test tìm kiếm/phân trang).
3. **Ảnh cá nhân cho header**: đã dùng ảnh Trần Minh Thọ do sinh viên cung cấp tại
   `src/main/resources/static/images/tran-minh-tho.png`, hiển thị qua `th:src`
   trong `src/main/resources/templates/fragments/header.html`.
   File ảnh gốc được giữ nguyên; CSS điều chỉnh khung hiển thị phần chân dung.
4. **Thông tin sinh viên ở footer**: đã điền sẵn "Trần Minh Thọ - MSSV 24133059" trong
   `src/main/resources/templates/fragments/footer.html`. Nếu muốn thêm Lớp, sửa trực
   tiếp trong file này.

## 3. Import vào Eclipse / STS 5.3.0 và chạy

1. File > Import... > Maven > Existing Maven Projects > chọn thư mục `springboot_5`
   (chứa `pom.xml`) > Finish. Eclipse/STS sẽ tự tải dependency và tạo cấu hình
   project (không cần copy `.project`/`.classpath` tay như các bài Dynamic Web
   Project trước, vì đây là project Maven "jar" thông thường của Spring Boot).
2. Click phải vào project > Run As > **Spring Boot App** (hoặc chuột phải vào
   `Springboot5Application.java` > Run As > Java Application).
3. Ứng dụng chạy ở cổng **8081** (khác cổng của Tomcat bên ngoài đang chạy các bài
   Servlet/JSP, để không xung đột) → mở trình duyệt: `http://localhost:8081/`

   (Nếu không có Eclipse/STS sẵn sàng, cũng có thể chạy bằng dòng lệnh
   `mvn spring-boot:run` tại thư mục project, nếu máy đã cài Maven.)

## 4. Các chức năng đã làm theo đúng yêu cầu đề bài

- **CRUD Category**: thêm (`/categories/add`), sửa (`/categories/edit/{id}`), xóa
  (`/categories/delete/{id}`), xem danh sách (`/categories`).
- **Tìm kiếm**: ô tìm kiếm theo tên danh mục (không phân biệt hoa/thường, tìm gần
  đúng) ngay trên trang danh sách.
- **Phân trang**: điều hướng Trước/Sau + số trang, giữ nguyên từ khóa tìm kiếm khi
  chuyển trang.
- **Thymeleaf + Thymeleaf Dialect (thymeleaf-layout-dialect)**: bố cục
  `layout/main-layout.html` chứa header/content/footer dùng chung; từng trang
  (`category/list.html`, `category/form.html`) chỉ khai báo phần
  `layout:fragment="content"` riêng của nó, y hệt cách làm trong slide bài giảng
  (Bước 7 - Sử dụng Thymeleaf Layout).
- **Bố cục trang**: header (đã có ảnh cá nhân - xem mục 2.3), content
  (bảng danh sách/tìm kiếm/phân trang/form), footer (thông tin sinh viên - xem
  mục 2.4).

## 5. Ghi chú

- Bản sửa ngày 14/09/2026: sửa biểu thức Thymeleaf gây lỗi 500 khi có nhiều trang;
  giới hạn `size` trong 1–100 và đưa trang vượt giới hạn về trang cuối;
  xử lý lưu danh mục không còn tồn tại; dùng `NVARCHAR` để lưu tên/mô tả tiếng Việt.
- Đã build với JDK 21 (mã nguồn vẫn đặt Java 17). Khi dùng STS, chọn JDK 21
  trong cấu hình chạy nếu máy chưa cài JDK 17.
- Chạy kiểm thử và đóng gói: `mvn package`. Chạy file đã đóng gói:
  `java -jar target/springboot5.jar`. Dừng bản đang chạy ở cổng 8081 trước khi chạy bản mới.
- Nếu Maven báo không tạo được repository tại `C:\.m2\repository`, dùng cache
  của tài khoản Windows: `mvn "-Dmaven.repo.local=%USERPROFILE%\.m2\repository" package`
  trong Command Prompt; với PowerShell dùng
  `mvn "-Dmaven.repo.local=$env:USERPROFILE\.m2\repository" package`.

- Không dùng Lombok (viết tay getter/setter) để tránh phải cài thêm Lombok
  plugin vào Eclipse, đồng nhất với các bài tập khác của môn.
- Comment trong code bằng tiếng Việt, tên class/method/biến bằng tiếng Anh theo
  chuẩn Java Naming Convention, đúng yêu cầu của môn học.

## 6. Đối chiếu đề bài và bài giảng 11_Thymeleaf.pdf

Đối chiếu ngày 14/09/2026 với đề sinh viên cung cấp và bài giảng 35 trang tại
`D:/WEB/14-09-2026/11_Thymeleaf.pdf`.

| Yêu cầu trong đề | Triển khai trong project | Kết quả |
| --- | --- | --- |
| Project Spring Boot | `pom.xml`, `Springboot5Application.java`; Maven JAR, Web MVC + JPA | Đạt |
| CRUD Category | `CategoryController`: list, add, save, edit, delete; lưu dữ liệu SQL Server | Đạt |
| Tìm kiếm có phân trang | Repository `findByNameContainingIgnoreCase` trả `Page<Category>`; `PageRequest`; link giữ `keyword` và `size` | Đạt |
| Thymeleaf làm view | `category/list.html`, `category/form.html`; `th:each`, `th:text`, `th:object`, `th:field` | Đạt |
| Thymeleaf Layout Dialect | Dependency `thymeleaf-layout-dialect`; `layout:decorate`, `layout:fragment` | Đạt |
| Header chứa hình sinh viên | Fragment `header.html`, ảnh `static/images/tran-minh-tho.png` | Đã bổ sung |
| Content | Danh sách, tìm kiếm, phân trang, form thêm/sửa trong fragment `content` | Đạt |
| Footer chứa thông tin sinh viên | Trần Minh Thọ, MSSV 24133059; môn học, giảng viên | Đạt |
| Tái sử dụng bố cục | `layout/main-layout.html` ghép header/footer bằng `th:replace`, dùng cho list và form | Đạt |

Các phần tương ứng trong bài giảng: trang 5–6 (form binding), 14–16 (fragment
và dependency), 22–27 (Entity/Repository/Service/Controller và phân trang),
28–33 (layout và view).

Phạm vi đánh giá là **đề bài đã cung cấp**, không khẳng định điểm chấm. Các ví dụ
MySQL, i18n (trang 34–35), Product và giao diện Bootstrap không được đề này nêu
là bắt buộc. Project dùng SQL Server, giao diện tiếng Việt và CSS riêng. Danh sách
và tìm kiếm được gộp chung ở `/categories`; không cần trùng tên file/URL mẫu trong slide.

Kiểm chứng: Maven build thành công và 6 kiểm thử MVC đạt. Đã kiểm tra CRUD,
tìm kiếm/phân trang và lưu tiếng Việt với SQL Server trong phiên sửa; các bản ghi
tạm đã được dọn. Khi demo phân trang mặc định 5 dòng/trang, tạo ít nhất 6 danh mục
hoặc truy cập `/categories?size=1` khi có ít nhất 2 danh mục. Bảng trống hoặc chỉ
có một trang sẽ không hiện thanh chuyển trang.
