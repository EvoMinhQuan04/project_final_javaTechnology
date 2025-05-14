package com.example.bds.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity @Getter @Setter
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    private int quantity;
    private LocalDateTime expiry;

    @OneToMany(mappedBy = "packagee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Available> availables;

    public Package(String name, double price, int quantity, LocalDateTime expiry) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.expiry = expiry;
    }


    private String formattedExpiry;

    // Getter và Setter cho formattedExpiry
    public String getFormattedExpiry() {
        return formattedExpiry;
    }

    public void setFormattedExpiry(String formattedExpiry) {
        this.formattedExpiry = formattedExpiry;
    }


    public Package() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }

    public void setExpiry(LocalDateTime expiry) {
        this.expiry = expiry;
    }

    public List<Available> getAvailables() {
        return availables;
    }

    public void setAvailables(List<Available> availables) {
        this.availables = availables;
    }
//    public void addAvailable(Available available) {
//        availables.add(available);
//        available.setPackagee(this);
//    }
//
//    public void removeAvailable(Available available) {
//        availables.remove(available);
//        available.setPackagee(null);
//    }
}
