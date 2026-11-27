package com.example.inventoryservice.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", unique = true, nullable = false, length = 50)
    private String code; // vd: NL01, NL02

    @Column(name = "name", nullable = false, length = 150)
    private String name; // vd: Lá trà Oolong, Sữa tươi tiệt trùng, Trân châu đen khô

    @Column(name = "unit", nullable = false, length = 30)
    private String unit; // kg, gam, lít, hộp, bịch

    @Column(name = "quantity_in_stock", nullable = false)
    private Double quantityInStock;

    @Column(name = "min_quantity", nullable = false)
    private Double minQuantity; // Ngưỡng tồn tối thiểu để phát cảnh báo

    @Column(name = "unit_price", precision = 12, scale = 2)
    private BigDecimal unitPrice; // Đơn giá nhập

    @Column(name = "status", length = 30)
    private String status = "IN_STOCK"; // IN_STOCK, LOW_STOCK, OUT_OF_STOCK

    public Ingredient() {
    }

    public Ingredient(Long id, String code, String name, String unit, Double quantityInStock, Double minQuantity, BigDecimal unitPrice, String status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.unit = unit;
        this.quantityInStock = quantityInStock;
        this.minQuantity = minQuantity;
        this.unitPrice = unitPrice;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Double quantityInStock) {
        this.quantityInStock = quantityInStock;
        updateStatus();
    }

    public Double getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Double minQuantity) {
        this.minQuantity = minQuantity;
        updateStatus();
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void updateStatus() {
        if (this.quantityInStock == null || this.quantityInStock <= 0) {
            this.status = "OUT_OF_STOCK";
        } else if (this.minQuantity != null && this.quantityInStock <= this.minQuantity) {
            this.status = "LOW_STOCK";
        } else {
            this.status = "IN_STOCK";
        }
    }
}
