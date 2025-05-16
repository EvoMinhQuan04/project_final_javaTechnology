package com.example.bds.controller;

import com.example.bds.model.LandForSale;
import com.example.bds.model.User;
import com.example.bds.service.LandForSaleService;
import com.example.bds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigInteger;
import java.util.List;

@Controller()
public class NhaDatBanController {
    @Autowired
    private LandForSaleService landForSaleService;
    @Autowired
    private UserService userService;



    @RequestMapping("/nha-dat-ban")
    public String nhadatban(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "CategoryIdsAsString", required = false) String categoryIdsAsString,
            @RequestParam(value = "PriceAsString", required = false) String priceAsString,
            @RequestParam(value = "AreaAsString", required = false) String areaAsString,
            @RequestParam(value = "RoomNumersAsString", required = false) String roomNumersAsString,
            @RequestParam(value = "province", required = false) String province,
            @RequestParam(value = "district", required = false) String district,
            Model model) {

        if (categoryIdsAsString != null && categoryIdsAsString.contains(",")) {
            categoryIdsAsString = categoryIdsAsString.split(",")[0];
        }

        if (priceAsString != null && priceAsString.contains(",")) {
            priceAsString = priceAsString.split(",")[0];
        }

        if (areaAsString != null && areaAsString.contains(",")) {
            areaAsString = areaAsString.split(",")[0];
        }

        System.out.println("Page: " + page);
        System.out.println("CategoryIdsAsString: " + categoryIdsAsString);
        System.out.println("PriceAsString: " + priceAsString);
        System.out.println("AreaAsString: " + areaAsString);
        System.out.println("RoomNumersAsString: " + roomNumersAsString);

        BigInteger minPrice = BigInteger.ZERO;
        int minArea = 0;
        BigInteger maxPrice = BigInteger.valueOf(Long.MAX_VALUE).multiply(BigInteger.TEN);
        int maxArea = Integer.MAX_VALUE;

        if (categoryIdsAsString == null) {
            categoryIdsAsString = "";
        }
        int numberRoom = 0;
        if (roomNumersAsString != null && !roomNumersAsString.isEmpty()) {
            try {
                numberRoom = Integer.parseInt(roomNumersAsString);
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Giá trị 'RoomNumersAsString' không hợp lệ");
            }
        }

        if(areaAsString != null) {
            switch (areaAsString) {
                case "1":
                    minArea = 0;
                    maxArea = 30;
                    break;
                case "2":
                    minArea = 30;
                    maxArea = 50;
                    break;
                case "3":
                    minArea = 50;
                    maxArea = 80;
                    break;
                case "4":
                    minArea = 80;
                    maxArea = 100;
                    break;
                case "5":
                    minArea = 100;
                    maxArea = 150;
                    break;
                case "6":
                    minArea = 150;
                    maxArea = 200;
                    break;
                case "7":
                    minArea = 200;
                    maxArea = 250;
                    break;
                case "8":
                    minArea = 250;
                    maxArea = 300;
                    break;
                case "9":
                    minArea = 300;
                    maxArea = 500;
                    break;
                case "10":
                    minArea = 500;
                    maxArea = Integer.MAX_VALUE;
                    break;
                default:
                    minArea = 0;
                    maxArea = Integer.MAX_VALUE;
                    break;
            }
        }

        System.out.println("minArea: " + minArea);
        System.out.println("maxArea: " + maxArea);

        if (priceAsString != null) {
            switch (priceAsString) {
                case "1":
                    minPrice = BigInteger.ZERO;
                    maxPrice = new BigInteger("100000000");
                    break;
                case "2":
                    minPrice = new BigInteger("100000000");
                    maxPrice = new BigInteger("200000000");
                    break;
                case "3":
                    minPrice = new BigInteger("200000000");
                    maxPrice = new BigInteger("300000000");
                    break;
                case "4":
                    minPrice = new BigInteger("300000000");
                    maxPrice = new BigInteger("500000000");
                    break;
                case "5":
                    minPrice = new BigInteger("500000000");
                    maxPrice = new BigInteger("800000000");
                    break;
                case "6":
                    minPrice = new BigInteger("800000000");
                    maxPrice = new BigInteger("1000000000");
                    break;
                case "7":
                    minPrice = new BigInteger("1000000000");
                    maxPrice = new BigInteger("2000000000");
                    break;
                case "8":
                    minPrice = new BigInteger("2000000000");
                    maxPrice = new BigInteger("1000000000000000000"); // Số rất lớn
                    break;
                case "0":
                    minPrice = BigInteger.ZERO;
                    maxPrice = BigInteger.ZERO;
                    break;
                default:
                    minPrice = BigInteger.ZERO;
                    maxPrice = BigInteger.valueOf(Long.MAX_VALUE).multiply(BigInteger.TEN); // Số cực lớn
                    break;
            }
        }

        Page<LandForSale> landForSalesPage;
        if (categoryIdsAsString.isEmpty() && priceAsString == null && areaAsString == null && roomNumersAsString == null && province == null && district == null) {
            landForSalesPage = landForSaleService.listLandSold(page);
        } else {
            System.out.println(province);
            landForSalesPage = landForSaleService.listLandSold(page, categoryIdsAsString, minPrice, maxPrice, minArea, maxArea, numberRoom, province, district);
        }

        System.out.println("Danh sách LandForSales:");
        for (LandForSale land : landForSalesPage.getContent()) {
            System.out.println("Title: " + land.getName() + ", Price: " + land.getPrice());
        }

        System.out.println("Total items: " + landForSalesPage.getTotalElements());

        model.addAttribute("landForSales", landForSalesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", landForSalesPage.getTotalPages());
        model.addAttribute("totalItems", landForSalesPage.getTotalElements());

        model.addAttribute("categoryIdsAsString", categoryIdsAsString);
        model.addAttribute("priceAsString", priceAsString);
        model.addAttribute("areaAsString", areaAsString);
        model.addAttribute("roomNumersAsString", roomNumersAsString);
        model.addAttribute("province", province);
        model.addAttribute("district", district);


        List<LandForSale> HCM = landForSaleService.getLandForSaleByProvinceSold("Hồ Chí Minh");
        List<LandForSale> HN = landForSaleService.getLandForSaleByProvinceSold("Hà Nội");
        List<LandForSale> BD = landForSaleService.getLandForSaleByProvinceSold("Bình Dương");
        List<LandForSale> DN = landForSaleService.getLandForSaleByProvinceSold("Đà Nắng");
        List<LandForSale> DongNai = landForSaleService.getLandForSaleByProvinceSold("Đồng Nai");

        List<LandForSale> HCM1 = landForSaleService.getLandForSaleByProvinceRent("Hồ Chí Minh");
        List<LandForSale> HN1 = landForSaleService.getLandForSaleByProvinceRent("Hà Nội");
        List<LandForSale> BD1 = landForSaleService.getLandForSaleByProvinceRent("Bình Dương");
        List<LandForSale> DN1 = landForSaleService.getLandForSaleByProvinceRent("Đà Nắng");
        List<LandForSale> DongNai1 = landForSaleService.getLandForSaleByProvinceRent("Đồng Nai");


        model.addAttribute("HCM", HCM);
        model.addAttribute("HN", HN);
        model.addAttribute("BD", BD);
        model.addAttribute("DN", DN);
        model.addAttribute("DongNai", DongNai);

        model.addAttribute("HCM1", HCM1);
        model.addAttribute("HN1", HN1);
        model.addAttribute("BD1", BD1);
        model.addAttribute("DN1", DN1);
        model.addAttribute("DongNai1", DongNai1);

        return "nha_dat_ban";
    }

    @GetMapping("/chi-tiet-nha-dat-ban/{id}")
    public String chitietnhadatban(@PathVariable("id") int id, Model model) {
        LandForSale landForSale = landForSaleService.findById(id);
        List<LandForSale> landForSaleList = landForSaleService.listLandSold();

        model.addAttribute("landForSalesList", landForSaleList);

        if (landForSale == null) {
            return "redirect:/error";
        }

        List<User> users = userService.getCustomers();
        for(User u : users){
            System.out.println(u.toString());
        }
        model.addAttribute("users", users);
        model.addAttribute("landForSale", landForSale);

        return "detail/detail_nha_dat_ban";
    }

    // API lấy lịch sử giá trung bình theo tháng của khu vực
    @GetMapping("/api/price-history")
    @ResponseBody
    public List<Double> getPriceHistory(@RequestParam String province, @RequestParam String district, @RequestParam(defaultValue = "12") int months, @RequestParam String type) {
        if (type != null && type.equalsIgnoreCase("Bán")) {
            return landForSaleService.getAveragePriceHistoryByDistrictAndTypeForSale(province, district, months);
        } else {
            return landForSaleService.getAveragePriceHistoryByDistrictAndType(province, district, type, months);
        }
    }

    // API lấy lịch sử giá trung bình, cao nhất, thấp nhất theo tháng của khu vực
    @GetMapping("/api/price-stats-history")
    @ResponseBody
    public List<List<Double>> getPriceStatsHistory(@RequestParam String province, @RequestParam String district, @RequestParam(defaultValue = "12") int months) {
        return landForSaleService.getPriceStatsHistoryByDistrictAndTypeForSale(province, district, months);
    }

}
