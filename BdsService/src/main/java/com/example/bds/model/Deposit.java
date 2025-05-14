package com.example.bds.model;

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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Deposit(Double soTien, LocalDateTime ngayNap, String tinhTrangThanhToan, User user) {
        this.soTien = soTien;
        this.ngayNap = ngayNap;
        this.tinhTrangThanhToan = tinhTrangThanhToan;
        this.user = user;
    }


    public Deposit() {
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
