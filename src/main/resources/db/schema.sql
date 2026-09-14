-- Chuyen bang do ban cu tao tu VARCHAR sang NVARCHAR, giu nguyen du lieu.
-- Hibernate ddl-auto=update khong tu doi kieu VARCHAR thanh NVARCHAR.
IF EXISTS (
    SELECT 1 FROM sys.columns
    WHERE object_id = OBJECT_ID(N'dbo.tl_category')
      AND name = N'category_name' AND system_type_id = 167
)
    ALTER TABLE dbo.tl_category ALTER COLUMN category_name NVARCHAR(100) NOT NULL;

IF EXISTS (
    SELECT 1 FROM sys.columns
    WHERE object_id = OBJECT_ID(N'dbo.tl_category')
      AND name = N'description' AND system_type_id = 167
)
    ALTER TABLE dbo.tl_category ALTER COLUMN description NVARCHAR(255) NULL;
