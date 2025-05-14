package com.example.bds.service;

import com.example.bds.model.ImageLand;
import com.example.bds.model.LandForSale;
import com.example.bds.repository.ImageLandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImageLandService {

    private  ImageLandRepository imageLandRepository;


    @Autowired
    public ImageLandService(ImageLandRepository imageLandRepository) {
        this.imageLandRepository = imageLandRepository;
    }

    public ImageLand createImageLand(ImageLand imageLand) {
        return imageLandRepository.save(imageLand);
    }

    public Optional<ImageLand> getImageLandById(int id) {
        return imageLandRepository.findById(id);
    }

    public Iterable<ImageLand> getAllImageLands() {
        return imageLandRepository.findAll();
    }


    public ImageLand updateImageLand(int id, ImageLand updatedImageLand) {
        return imageLandRepository.findById(id)
                .map(existingImageLand -> {

                    return imageLandRepository.save(existingImageLand);
                }).orElseThrow(() -> new RuntimeException("ImageLand not found"));
    }


    public void deleteImageLand(int id) {
        imageLandRepository.deleteById(id);
    }

    public void deleteImageLandsByLandForSale(LandForSale landForSale) {
        List<ImageLand> imageLands = imageLandRepository.findByLandForSale(landForSale);
        imageLandRepository.deleteAll(imageLands);
    }
}


