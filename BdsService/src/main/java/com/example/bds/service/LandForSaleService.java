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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
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

    /**
     * Lấy lịch sử giá trung bình theo tháng của một khu vực (district, province)
     * @param province tên tỉnh/thành
     * @param district tên quận/huyện
     * @param months số tháng gần nhất
     * @return List<Double> giá trung bình từng tháng (mới nhất đến cũ)
     */
    public List<Double> getAveragePriceHistoryByDistrict(String province, String district, int months) {
        List<LandForSale> all = landForSaleRepository.findByProvinceAndDistrict(province, district);
        // Giả sử datePosted luôn có dữ liệu
        Map<String, List<Double>> monthToPrices = new TreeMap<>(Collections.reverseOrder());
        for (LandForSale land : all) {
            if (land.getDatePosted() == null) continue;
            String key = land.getDatePosted().getYear() + "-" + String.format("%02d", land.getDatePosted().getMonthValue());
            monthToPrices.computeIfAbsent(key, k -> new ArrayList<>()).add(land.getPrice() / land.getArea());
        }
        List<Double> result = new ArrayList<>();
        int count = 0;
        for (List<Double> prices : monthToPrices.values()) {
            if (count++ >= months) break;
            double avg = prices.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            result.add(avg);
        }
        return result;
    }

    /**
     * Lấy lịch sử giá trung bình theo tháng của một khu vực (district, province) và loại hình (Bán/Cho thuê)
     */
    public List<Double> getAveragePriceHistoryByDistrictAndType(String province, String district, String type, int months) {
        List<LandForSale> all = landForSaleRepository.findByProvinceAndDistrict(province, district);
        // Lọc đúng loại hình
        all = all.stream().filter(l -> l.getType() != null && l.getType().equalsIgnoreCase(type)).toList();
        Map<String, List<Double>> monthToPrices = new TreeMap<>(Collections.reverseOrder());
        for (LandForSale land : all) {
            if (land.getDatePosted() == null) continue;
            String key = land.getDatePosted().getYear() + "-" + String.format("%02d", land.getDatePosted().getMonthValue());
            monthToPrices.computeIfAbsent(key, k -> new ArrayList<>()).add(land.getPrice());
        }
        List<Double> result = new ArrayList<>();
        int count = 0;
        for (List<Double> prices : monthToPrices.values()) {
            if (count++ >= months) break;
            double avg = prices.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            result.add(avg);
        }
        return result;
    }

    /**
     * Lấy lịch sử giá bán trung bình trên mỗi mét vuông theo tháng của một khu vực (district, province) và loại hình Bán
     */
    public List<Double> getAveragePriceHistoryByDistrictAndTypeForSale(String province, String district, int months) {
        List<LandForSale> all = landForSaleRepository.findByProvinceAndDistrict(province, district);
        // Lọc đúng loại hình Bán
        all = all.stream().filter(l -> l.getType() != null && l.getType().equalsIgnoreCase("Bán")).toList();
        Map<String, List<LandForSale>> monthToLands = new TreeMap<>(Collections.reverseOrder());
        for (LandForSale land : all) {
            if (land.getDatePosted() == null) continue;
            String key = land.getDatePosted().getYear() + "-" + String.format("%02d", land.getDatePosted().getMonthValue());
            monthToLands.computeIfAbsent(key, k -> new ArrayList<>()).add(land);
        }
        List<Double> result = new ArrayList<>();
        int count = 0;
        for (List<LandForSale> lands : monthToLands.values()) {
            if (count++ >= months) break;
            double totalPrice = lands.stream().mapToDouble(LandForSale::getPrice).sum();
            double totalArea = lands.stream().mapToDouble(LandForSale::getArea).sum();
            double avg = (totalArea > 0) ? totalPrice / totalArea : 0;
            result.add(avg);
        }
        return result;
    }

    /**
     * Lấy lịch sử giá bán trung bình, cao nhất, thấp nhất trên mỗi mét vuông theo tháng của một khu vực (district, province) và loại hình Bán
     * Trả về List<List<Double>>: [avgList, maxList, minList]
     */
    public List<List<Double>> getPriceStatsHistoryByDistrictAndTypeForSale(String province, String district, int months) {
        List<LandForSale> all = landForSaleRepository.findByProvinceAndDistrict(province, district);
        // Lọc đúng loại hình Bán
        all = all.stream().filter(l -> l.getType() != null && l.getType().equalsIgnoreCase("Bán")).toList();
        Map<String, List<LandForSale>> monthToLands = new TreeMap<>(Collections.reverseOrder());
        for (LandForSale land : all) {
            if (land.getDatePosted() == null) continue;
            String key = land.getDatePosted().getYear() + "-" + String.format("%02d", land.getDatePosted().getMonthValue());
            monthToLands.computeIfAbsent(key, k -> new ArrayList<>()).add(land);
        }
        List<Double> avgList = new ArrayList<>();
        List<Double> maxList = new ArrayList<>();
        List<Double> minList = new ArrayList<>();
        int count = 0;
        for (List<LandForSale> lands : monthToLands.values()) {
            if (count++ >= months) break;
            double totalPrice = lands.stream().mapToDouble(LandForSale::getPrice).sum();
            double totalArea = lands.stream().mapToDouble(LandForSale::getArea).sum();
            double avg = (totalArea > 0) ? totalPrice / totalArea : 0;
            avgList.add(avg);
            // Tính max, min giá/m2
            double max = lands.stream().mapToDouble(l -> l.getArea() > 0 ? l.getPrice() / l.getArea() : 0).max().orElse(0);
            double min = lands.stream().mapToDouble(l -> l.getArea() > 0 ? l.getPrice() / l.getArea() : 0).min().orElse(0);
            maxList.add(max);
            minList.add(min);
        }
        List<List<Double>> result = new ArrayList<>();
        result.add(avgList);
        result.add(maxList);
        result.add(minList);
        return result;
    }

    /**
     * Lấy lịch sử giá thuê trung bình, cao nhất, thấp nhất theo tháng của một khu vực (district, province) và loại hình Cho thuê
     * Trả về List<List<Double>>: [avgList, maxList, minList]
     */
    public List<List<Double>> getPriceStatsHistoryByDistrictAndTypeForRent(String province, String district, int months) {
        List<LandForSale> all = landForSaleRepository.findByProvinceAndDistrict(province, district);
        // Lọc đúng loại hình Cho thuê
        all = all.stream().filter(l -> l.getType() != null && l.getType().equalsIgnoreCase("Cho thuê")).toList();
        Map<String, List<LandForSale>> monthToLands = new TreeMap<>(Collections.reverseOrder());
        for (LandForSale land : all) {
            if (land.getDatePosted() == null) continue;
            String key = land.getDatePosted().getYear() + "-" + String.format("%02d", land.getDatePosted().getMonthValue());
            monthToLands.computeIfAbsent(key, k -> new ArrayList<>()).add(land);
        }
        List<Double> avgList = new ArrayList<>();
        List<Double> maxList = new ArrayList<>();
        List<Double> minList = new ArrayList<>();
        int count = 0;
        for (List<LandForSale> lands : monthToLands.values()) {
            if (count++ >= months) break;
            // Trung bình giá thuê (theo tổng giá/tháng)
            double avg = lands.stream().mapToDouble(LandForSale::getPrice).average().orElse(0);
            avgList.add(avg);
            double max = lands.stream().mapToDouble(LandForSale::getPrice).max().orElse(0);
            double min = lands.stream().mapToDouble(LandForSale::getPrice).min().orElse(0);
            maxList.add(max);
            minList.add(min);
        }
        List<List<Double>> result = new ArrayList<>();
        result.add(avgList);
        result.add(maxList);
        result.add(minList);
        return result;
    }

}