package com.example.bds.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
public class LandForSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    private double area;
    private String province;
    private String district;
    private String ward;
    private String interior;
    private int numberOfToilets;
    private int numberOfBedRooms;

    @Column(length = 100000)
    private String description;
    private LocalDateTime datePosted;
    private String type;
    private String propertyType;

    private String legal;

    private Double latitude;
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<ImageLand> getImages() {
        return images;
    }

    public void setImages(List<ImageLand> images) {
        this.images = images;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String loai) {
        this.propertyType = loai;
    }

    @OneToMany(mappedBy = "landForSale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageLand> images = new ArrayList<>();



    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User broker;

    @ManyToOne
    @JoinColumn(name = "available_id")
    private Available available;

    public LandForSale(String name, double price, double area, String province, String district, String ward,
                       String interior, int numberOfToilets, int numberOfBedRooms, String description, LocalDateTime datePoste,
                       String type, String legal) {
        this.name = name;
        this.price = price;
        this.area = area;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.interior = interior;
        this.numberOfToilets = numberOfToilets;
        this.numberOfBedRooms = numberOfBedRooms;
        this.description = description;
        this.datePosted = datePoste;
        this.type = type;
        this.legal = legal;
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

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getInterior() {
        return interior;
    }

    public void setInterior(String interior) {
        this.interior = interior;
    }

    public int getNumberOfToilets() {
        return numberOfToilets;
    }

    public void setNumberOfToilets(int numberOfToilets) {
        this.numberOfToilets = numberOfToilets;
    }

    public int getNumberOfBedRooms() {
        return numberOfBedRooms;
    }

    public void setNumberOfBedRooms(int numberOfBedRooms) {
        this.numberOfBedRooms = numberOfBedRooms;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLegal() {
        return legal;
    }

    public void setLegal(String legal) {
        this.legal = legal;
    }

    public User getBroker() {
        return broker;
    }

    public void setBroker(User broker) {
        this.broker = broker;
    }

    public Available getAvailable() {
        return available;
    }

    public void setAvailable(Available available) {
        this.available = available;
    }

    public LandForSale() {
    }
//    public void addImage(ImageLand imageLand) {
//        imageLands.add(imageLand);
//        imageLand.setLandForSale(this);
//    }
//
//    public void removeImage(ImageLand imageLand) {
//        imageLands.remove(imageLand);
//        imageLand.setLandForSale(null);
//    }

    public String formatToReadableUnit(double number) {
        if (number >= 1000000000) {
            return (number / 1000000000) + " tỷ";
        } else if (number >= 1000000) {
            return (number / 1000000) + " triệu";
        } else if (number >= 1000) {
            return (number / 1000) + " ngàn";
        } else {
            return number + " đồng";
        }
    }

    public String getRelativeTime(LocalDateTime datePosted) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        long daysBetween = ChronoUnit.DAYS.between(datePosted.toLocalDate(), currentDateTime.toLocalDate());

        if (daysBetween == 0) {
            return "Đăng hôm nay";
        } else if (daysBetween == 1) {
            return "Đăng hôm qua";
        } else if (daysBetween < 30) {
            return "Đăng " + daysBetween + " ngày trước";
        } else {
            return "Đăng vào " + datePosted.toLocalDate().toString(); // Hiển thị ngày đăng theo định dạng yyyy-MM-dd
        }
    }

}
