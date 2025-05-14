package com.example.bds.model;

import jakarta.persistence.*;

@Entity
public class ImageLand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String imageLink;

    @ManyToOne
    @JoinColumn(name = "landForSale_id")
    private LandForSale landForSale;


    public ImageLand(String imageLink) {
        this.imageLink = imageLink;
    }
    public ImageLand(String imageLink, LandForSale landForSale) {
        this.imageLink = imageLink;
        this.landForSale = landForSale;
    }

    public ImageLand() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public LandForSale getLandForSale() {
        return landForSale;
    }

    public void setLandForSale(LandForSale landForSale) {
        this.landForSale = landForSale;
    }
}
