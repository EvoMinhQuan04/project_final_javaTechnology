package com.example.bds.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String summaryOfContent;
    @Column(length = 1000000)
    private String content;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageNews> images = new ArrayList<>();


    public List<ImageNews> getImages() {
        return images;
    }

    public void setImages(List<ImageNews> images) {
        this.images = images;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User broker;

    @ManyToOne
    @JoinColumn(name = "available_id")
    private Available available;

    @Column(nullable = false)
    private LocalDateTime publishDate;


    private String formattedExpiry;

    public String getFormattedExpiry() {
        return formattedExpiry;
    }

    public void setFormattedExpiry(String formattedExpiry) {
        this.formattedExpiry = formattedExpiry;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummaryOfContent() {
        return summaryOfContent;
    }

    public void setSummaryOfContent(String summaryOfContent) {
        this.summaryOfContent = summaryOfContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public News() {
    }

    public News(String title, String summaryOfContent, String content) {
        this.title = title;
        this.summaryOfContent = summaryOfContent;
        this.content = content;
    }


}
