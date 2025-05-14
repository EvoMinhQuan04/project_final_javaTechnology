package com.example.bds.repository;

import com.example.bds.model.ImageNews;
import com.example.bds.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageNewsRepository extends JpaRepository<ImageNews, Integer> {
    List<ImageNews> findByNews(News news);

}
