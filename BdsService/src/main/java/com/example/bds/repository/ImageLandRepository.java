package com.example.bds.repository;

import com.example.bds.model.ImageLand;
import com.example.bds.model.LandForSale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;
public interface ImageLandRepository extends JpaRepository<ImageLand, Integer> {
    List<ImageLand> findByLandForSale(LandForSale landForSale);
}
