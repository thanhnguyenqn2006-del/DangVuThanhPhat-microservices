-- Dữ liệu mẫu phục vụ nghiệp vụ Quán Trà Sữa (Lab 03 - Mục 8)
USE product_db;

-- 1. Bảng Categories
CREATE TABLE IF NOT EXISTS categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. Bảng Products
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    price DECIMAL(12, 2) NOT NULL,
    image_url VARCHAR(255),
    status VARCHAR(50) DEFAULT 'AVAILABLE',
    category_id BIGINT,
    CONSTRAINT fk_products_categories FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. Bảng Toppings
CREATE TABLE IF NOT EXISTS toppings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(12, 2) NOT NULL,
    status VARCHAR(50) DEFAULT 'AVAILABLE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. Chèn Categories
INSERT INTO categories (id, name, description) VALUES
(1, 'Trà Sữa Truyền Thống', 'Các dòng trà sữa béo thơm đậm vị trà truyền thống'),
(2, 'Trà Trái Cây Nhiệt Đới', 'Trà thanh mát kết hợp trái cây tươi tự nhiên'),
(3, 'Cà Phê & Macchiato', 'Cà phê thơm lừng và lớp kem béo ngậy'),
(4, 'Đá Xay & Freeze', 'Đồ uống đá xay mát lạnh giải nhiệt')
ON DUPLICATE KEY UPDATE name=VALUES(name), description=VALUES(description);

-- 5. Chèn Products (10 món trà sữa đặc sắc)
INSERT INTO products (id, name, description, price, image_url, status, category_id) VALUES
(1, 'Trà Sữa Trân Châu Hoàng Kim', 'Trà sữa đậm vị kèm trân châu hoàng kim dai giòn', 35000.00, 'https://images.unsplash.com/photo-1558857563-b37cfb299e56?w=500', 'AVAILABLE', 1),
(2, 'Trà Sữa Oolong Nướng', 'Vị trà oolong nướng thơm lừng kết hợp sữa béo ngậy', 38000.00, 'https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=500', 'AVAILABLE', 1),
(3, 'Trà Sữa Matcha Đậu Đỏ', 'Matcha Nhật Bản nguyên chất hòa quyện đậu đỏ ngọt bùi', 42000.00, 'https://images.unsplash.com/photo-1536256263959-770b48d82b0a?w=500', 'AVAILABLE', 1),
(4, 'Sữa Tươi Trân Châu Đường Đen', 'Sữa tươi thanh trùng cùng đường đen cô đặc thơm ngọt', 40000.00, 'https://images.unsplash.com/photo-1541658016709-82535e94bc69?w=500', 'AVAILABLE', 1),
(5, 'Trà Đào Cam Sả', 'Vị trà đào thơm ngát quyện cam tươi và hương sả nồng nàn', 39000.00, 'https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?w=500', 'AVAILABLE', 2),
(6, 'Trà Vải Hoa Lài', 'Trà lài thanh tao hòa cùng vải thiều ngọt mọng nước', 38000.00, 'https://images.unsplash.com/photo-1556881286-fc6915169721?w=500', 'AVAILABLE', 2),
(7, 'Trà Dâu Tằm Pha Lê', 'Dâu tằm tươi mọng nước kết hợp thạch pha lê giòn dai', 45000.00, 'https://images.unsplash.com/photo-1497534446932-c925b458314e?w=500', 'AVAILABLE', 2),
(8, 'Cà Phê Muối Macchiato', 'Cà phê phin truyền thống phủ lớp kem muối béo mặn', 32000.00, 'https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?w=500', 'AVAILABLE', 3),
(9, 'Freeze Trà Xanh Đá Xay', 'Trà xanh đá xay mát lạnh phủ thạch jelly và kem tươi', 49000.00, 'https://images.unsplash.com/photo-1577805947697-89e18249d767?w=500', 'AVAILABLE', 4),
(10, 'Trà Sữa Khoai Môn Dẻo', 'Khoai môn tươi dẻo bùi béo kết hợp trà sữa', 40000.00, 'https://images.unsplash.com/photo-1579954115545-a95591f28bfc?w=500', 'AVAILABLE', 1)
ON DUPLICATE KEY UPDATE name=VALUES(name), price=VALUES(price), category_id=VALUES(category_id);

-- 6. Chèn Toppings (6 loại topping)
INSERT INTO toppings (id, name, price, status) VALUES
(1, 'Trân Châu Đen', 5000.00, 'AVAILABLE'),
(2, 'Trân Châu Hoàng Kim', 7000.00, 'AVAILABLE'),
(3, 'Thạch Phô Mai Tươi', 10000.00, 'AVAILABLE'),
(4, 'Pudding Trứng', 8000.00, 'AVAILABLE'),
(5, 'Thạch Củ Năng', 7000.00, 'AVAILABLE'),
(6, 'Kem Cheese Macchiato', 12000.00, 'AVAILABLE')
ON DUPLICATE KEY UPDATE name=VALUES(name), price=VALUES(price);
