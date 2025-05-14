package com.example.bds.service;

import com.example.bds.model.*;
import com.example.bds.repository.AvailableRepository;
import com.example.bds.repository.ImageLandRepository;
import com.example.bds.repository.LandForSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LandForSaleService {

    @Autowired
    private LandForSaleRepository landForSaleRepository;


    public LandForSale createLandForSale(LandForSale landForSale) {
        return landForSaleRepository.save(landForSale);
    }


    public List<LandForSale> listLandByBrokerSold(Long userId) {
        List<String> types = Arrays.asList("Bán");
        return landForSaleRepository.findByBrokerIdAndTypeIn(userId, types);
    }


    public List<LandForSale> listLandSold() {
        List<String> types = Arrays.asList("Bán");
        return landForSaleRepository.findByTypeIn( types);
    }

    public Page<LandForSale> listLandSold(int page) {
        List<String> types = Arrays.asList("Bán");
        Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(Sort.Order.desc("datePosted")));
        return landForSaleRepository.findByTypeIn(types, pageable);
    }


    public Page<LandForSale> listLandSold(int page, String propertyTypesAsString, BigInteger minPrice, BigInteger maxPrice,
                                          int minArea, int maxArea, int numberOfBedRooms, String province, String district) {
        List<String> propertyTypes = (propertyTypesAsString != null && !propertyTypesAsString.isEmpty())
                ? Arrays.asList(propertyTypesAsString.split(","))
                : null;

        Pageable pageable = PageRequest.of(page - 1, 5);

        return landForSaleRepository.findByPropertyTypesAndPriceAndTypesAndAreaAndNumberOfBedRoomsAndLocation(
                propertyTypes, minPrice, maxPrice, Arrays.asList("Bán"), minArea, maxArea, numberOfBedRooms, province, district, pageable);
    }

    public List<LandForSale> getLandForSaleByProvinceSold(String province) {
        return landForSaleRepository.findByProvinceLike(province, Arrays.asList("Bán"));
    }

    public List<LandForSale> getLandForSaleByProvinceRent(String province) {
        return landForSaleRepository.findByProvinceLike(province, Arrays.asList("Cho thuê"));
    }



    public List<LandForSale> listLandRent() {
        List<String> types = Arrays.asList( "Cho thuê");
        return landForSaleRepository.findByTypeIn( types);
    }

    public Page<LandForSale> listLandRent(int page) {
        List<String> types = Arrays.asList("Cho thuê");
        Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(Sort.Order.desc("datePosted")));

        return landForSaleRepository.findByTypeIn(types, pageable);
    }




    public Page<LandForSale> listLandRent(int page, String propertyTypesAsString, BigInteger minPrice, BigInteger maxPrice,
                                          int minArea, int maxArea, int numberOfBedRooms, String province, String district) {
        List<String> propertyTypes = (propertyTypesAsString != null && !propertyTypesAsString.isEmpty())
                ? Arrays.asList(propertyTypesAsString.split(","))
                : null;

        Pageable pageable = PageRequest.of(page - 1, 5);

        return landForSaleRepository.findByPropertyTypesAndPriceAndTypesAndAreaAndNumberOfBedRoomsAndLocation(
                propertyTypes, minPrice, maxPrice, Arrays.asList("Cho thuê"), minArea, maxArea, numberOfBedRooms, province, district, pageable);
    }



    public List<LandForSale> listLandByBrokerRent(Long userId) {
        List<String> types = Arrays.asList( "Cho thuê");
        return landForSaleRepository.findByBrokerIdAndTypeIn(userId, types);
    }

    public Optional<LandForSale> getLandForSaleById(int id) {
        return landForSaleRepository.findById(id);
    }

    public Iterable<LandForSale> getAllLandForSales() {
        return landForSaleRepository.findAll();
    }


    public void updateLandForSale(int id, LandForSale updatedLandForSale) {
        LandForSale l  =landForSaleRepository.findById(id).get();
        l.setArea(updatedLandForSale.getArea());
        l.setName(updatedLandForSale.getName());
        l.setDescription(updatedLandForSale.getDescription());
        l.setInterior(updatedLandForSale.getInterior());
        l.setLegal(updatedLandForSale.getLegal());
        l.setPrice(updatedLandForSale.getPrice());
        l.setNumberOfBedRooms(updatedLandForSale.getNumberOfBedRooms());
        l.setNumberOfToilets(updatedLandForSale.getNumberOfToilets());
        landForSaleRepository.save(l);


    }

    public LandForSale findById(int id) {
        return landForSaleRepository.findById(id).get();
    }
    public void deleteLandById(int id) {
        landForSaleRepository.deleteById(id);
    }


    public void deleteLandForSale(int id) {
        landForSaleRepository.deleteById(id);
    }

    @Autowired
    private AvailableService availableService;

    @Autowired
    private UserService userService;

    @Autowired
    private AvailableRepository availableRepository;
    @Autowired
    private ImageLandRepository imageLandRepository;


    public void postLandForSale(LandForSale landForSale, Long userId, int availableId, List<String> imageLinks) {

        Available available = availableRepository.findByOrderIdAndBroker_Id(availableId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Available not found or does not belong to user"));


        if (available.getQuantityAvailable() <= 0) {
            throw new IllegalArgumentException("No available quantity left for posting.");
        }


        available.setQuantityAvailable(available.getQuantityAvailable() - 1);
        availableRepository.save(available);


        User user = userService.findById(userId);
        landForSale.setAvailable(available);
        landForSale.setBroker(user);
        LandForSale savedLand = landForSaleRepository.save(landForSale);


        if (imageLinks != null && !imageLinks.isEmpty()) {
            List<ImageLand> imageLands = imageLinks.stream()
                    .map(link -> new ImageLand(link, savedLand))
                    .collect(Collectors.toList());
            imageLandRepository.saveAll(imageLands);
        }
    }



}