package com.example.bds.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Available {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private String statusPayment;
    private LocalDateTime purchaseDate;
    private int quantityAvailable;
    private Double total;
    private LocalDateTime expirationDate;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User broker;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false)
    private Package packagee;


    private String formattedPurchase;
    private String formattedExpiry;

    public String getFormattedPurchase() {
        return formattedPurchase;
    }

    public void setFormattedPurchase(String formattedPurchase) {
        this.formattedPurchase = formattedPurchase;
    }

    public String getFormattedExpiry() {
        return formattedExpiry;
    }

    public void setFormattedExpiry(String formattedExpiry) {
        this.formattedExpiry = formattedExpiry;
    }

    public Available(String statusPayment, LocalDateTime purchaseDate, int quantityAvailable,
                     Double total, LocalDateTime expirationDate) {
        this.statusPayment = statusPayment;
        this.purchaseDate = purchaseDate;
        this.quantityAvailable = quantityAvailable;
        this.total = total;
        this.expirationDate = expirationDate;
    }

    public Available() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public User getBroker() {
        return broker;
    }

    public void setBroker(User broker) {
        this.broker = broker;
    }

    public Package getPackagee() {
        return packagee;
    }

    public void setPackagee(Package packagee) {
        this.packagee = packagee;
    }

    //    public void addNews(News news) {
//        newsList.add(news);
//        news.setAvailable(this);
//    }
//
//    public void removeNews(News news) {
//        newsList.remove(news);
//        news.setAvailable(null);
//    }
//
//    public void addLandForSale(LandForSale landForSale) {
//        landForSaleList.add(landForSale);
//        landForSale.setAvailable(this);
//    }
//
//    public void removeLandForSale(LandForSale landForSale) {
//        landForSaleList.remove(landForSale);
//        landForSale.setAvailable(null);
//    }
//
//    public void addProject(Project project) {
//        projectList.add(project);
//        project.setAvailable(this);
//    }
//
//    public void removeProject(Project project) {
//        projectList.remove(project);
//        project.setAvailable(null);
//    }
//
//    public void addLandForRent(LandForRent landForRent) {
//        landForRentList.add(landForRent);
//        landForRent.setAvailable(this);
//    }
//
//    public void removeLandForRent(LandForRent landForRent) {
//        landForRentList.remove(landForRent);
//        landForRent.setAvailable(null);
//    }
}
