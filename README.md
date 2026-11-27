# Hệ Thống Microservices Quản Lý Quán Trà Sữa (Milk Tea Shop)

Đồ án môn học: Lập trình Web 3 – Spring Boot & Microservices (Lab 02)
Sinh viên thực hiện: **Đặng Vũ Thành Phát** - MSSV: **2124110103**

---

## 🏗️ Cấu trúc các Microservices (Backend):
Hệ thống bao gồm 3 Business Microservices độc lập:

1. **`product-service` (Service 1 - Menu / Sản phẩm)**
   - **Cổng:** `8081`
   - **Hệ quản trị CSDL:** MySQL (`product_db`)
   - **Chức năng:** Quản lý danh mục đồ uống (`categories`), món trà sữa (`products`), và danh sách topping (`toppings`).
   - **APIs:** `/api/categories`, `/api/products`, `/api/toppings`

2. **`order-service` (Service 2 - Đơn hàng & Giỏ hàng)**
   - **Cổng:** `8082`
   - **Hệ quản trị CSDL:** Microsoft SQL Server (`order_db`)
   - **Chức năng:** Quản lý giỏ hàng (`cart_items`), đặt hàng (`orders`), và chi tiết ly trà sữa kèm Size, % Đường, % Đá, Topping (`order_items`).
   - **APIs:** `/api/orders`, `/api/cart`

3. **`inventory-service` (Service 3 - Quản lý Kho nguyên liệu - Câu F)**
   - **Cổng:** `8083`
   - **Hệ quản trị CSDL:** MySQL (`inventory_db`)
   - **Chức năng:** Quản lý nguyên vật liệu pha chế (`ingredients`: lá trà, sữa, trân châu, ly, ống hút,...), cảnh báo nguyên liệu sắp hết tồn kho (`/low-stock`), và lập phiếu nhập/xuất kho (`stock_transactions`).
   - **APIs:** `/api/ingredients`, `/api/stock-transactions`

---

## 🚀 Hướng dẫn chạy các Service:

### Chạy Service 1 (Product Service):
```bash
cd backend/product-service
mvn spring-boot:run
```

### Chạy Service 2 (Order Service):
```bash
cd backend/order-service
mvn spring-boot:run
```

### Chạy Service 3 (Inventory Service):
```bash
cd backend/inventory-service
mvn spring-boot:run
```
