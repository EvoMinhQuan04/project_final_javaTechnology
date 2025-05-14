package com.example.bds.model;

import jakarta.persistence.*;

@Entity
public class ImageNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String imageLink;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;

    public ImageNews(String imageLink) {
        this.imageLink = imageLink;
    }


    public ImageNews() {
    }

    public int getId() {
        return id;
    }

    public ImageNews(String imageLink, News news) {
        this.imageLink = imageLink;
        this.news = news;
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

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}
