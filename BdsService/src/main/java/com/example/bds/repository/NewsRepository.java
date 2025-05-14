package com.example.bds.repository;

import com.example.bds.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer> {
    List<News> findByBrokerId(Long userId);

    // Trong NewsRepository
    @Query("SELECT n FROM News n ORDER BY n.publishDate DESC LIMIT 1")
    News findLatestNews();

    @Query("SELECT n FROM News n ORDER BY n.publishDate DESC LIMIT 4")
    List<News> findTop4News();

}
