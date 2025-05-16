package com.example.pay.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double soTien;

    @Column(nullable = false)
    private LocalDateTime ngayNap;

    @Column(nullable = false)
    private String tinhTrangThanhToan;

    @Column(nullable = false)
    private Long userId; // Lưu ID của User thay vì đối tượng User

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSoTien() {
        return soTien;
    }

    public void setSoTien(Double soTien) {
        this.soTien = soTien;
    }

    public LocalDateTime getNgayNap() {
        return ngayNap;
    }

    public void setNgayNap(LocalDateTime ngayNap) {
        this.ngayNap = ngayNap;
    }

    public String getTinhTrangThanhToan() {
        return tinhTrangThanhToan;
    }

    public void setTinhTrangThanhToan(String tinhTrangThanhToan) {
        this.tinhTrangThanhToan = tinhTrangThanhToan;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}