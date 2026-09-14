-- Script tao bang tl_category cho bai tap Thymeleaf (CRUD + tim kiem + phan trang)
-- Chi can chay tay trong SSMS NEU spring.jpa.hibernate.ddl-auto=update khong tu tao
-- duoc bang khi chay ung dung lan dau (da tung gap truong hop nay o cac bai tap khac).

USE webst2;
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'tl_category')
BEGIN
    CREATE TABLE tl_category (
        category_id    BIGINT IDENTITY(1,1) PRIMARY KEY,
        category_name  NVARCHAR(100) NOT NULL,
        description    NVARCHAR(255) NULL
    );
END
GO

-- Seed du lieu mau de test tim kiem & phan trang (8 dong -> voi size=5 se co 2 trang)
IF NOT EXISTS (SELECT * FROM tl_category)
BEGIN
    INSERT INTO tl_category (category_name, description) VALUES
    (N'Đồ uống',           N'Các loại nước giải khát, cà phê, trà'),
    (N'Thực phẩm khô',     N'Mì gói, bún khô, gạo, ngũ cốc'),
    (N'Gia vị',            N'Nước mắm, hạt nêm, bột ngọt'),
    (N'Đồ hộp',            N'Cá hộp, thịt hộp, rau củ đóng hộp'),
    (N'Bánh kẹo',          N'Các loại bánh, kẹo, snack'),
    (N'Sữa & chế phẩm',    N'Sữa tươi, sữa chua, phô mai'),
    (N'Đông lạnh',         N'Thực phẩm đông lạnh, kem'),
    (N'Vệ sinh cá nhân',   N'Dầu gội, sữa tắm, kem đánh răng');
END
GO
