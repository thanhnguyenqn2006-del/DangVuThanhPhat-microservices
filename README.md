# Hệ Thống Microservices Quản Lý Quán Trà Sữa (Milk Tea Shop)

Đồ án môn học: Lập trình Web 3 – Spring Boot & Microservices (Lab 02)

## Cấu trúc thư mục:
- `backend/`: Chứa các Business Microservices độc lập
  - `product-service/`: Quản lý thực đơn trà sữa, loại sản phẩm, topping, kích thước (Port: 8081, Database: MySQL)
  - `order-service/`: Quản lý đơn đặt hàng, chi tiết ly trà sữa, giỏ hàng (Port: 8082, Database: SQL Server)
- `frontend/`:
  - `admin/`: Giao diện quản trị cho quản lý và nhân viên
  - `client/`: Giao diện khách hàng đặt món online / tại bàn

## Hướng dẫn chạy các Service:
### 1. Product Service:
```bash
cd backend/product-service
mvn spring-boot:run
```
Service chạy tại: `http://localhost:8081`

### 2. Order Service:
```bash
cd backend/order-service
mvn spring-boot:run
```
Service chạy tại: `http://localhost:8082`
