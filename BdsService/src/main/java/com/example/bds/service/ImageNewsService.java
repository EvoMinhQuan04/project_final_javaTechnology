package com.example.bds.service;

import com.example.bds.model.ImageNews;
import com.example.bds.model.News;
import com.example.bds.repository.ImageNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageNewsService {

    @Autowired
    private ImageNewsRepository imageNewsRepository;


    public ImageNews createImageNews(ImageNews imageNews) {
        return imageNewsRepository.save(imageNews);
    }


    public Optional<ImageNews> getImageNewsById(int id) {
        return imageNewsRepository.findById(id);
    }

    public Iterable<ImageNews> getAllImageNewss() {
        return imageNewsRepository.findAll();
    }


    public ImageNews updateImageNews(int id, ImageNews updatedImageNews) {
        return imageNewsRepository.findById(id)
                .map(existingImageNews -> {
                    // Update fields here
                    return imageNewsRepository.save(existingImageNews);
                }).orElseThrow(() -> new RuntimeException("ImageNews not found"));
    }


    public void deleteImageNews(int id) {
        imageNewsRepository.deleteById(id);
    }

    public void deleteImageLandsByNews(News news) {
        List<ImageNews> imageLands = imageNewsRepository.findByNews(news);
        imageNewsRepository.deleteAll(imageLands);
    }
}
