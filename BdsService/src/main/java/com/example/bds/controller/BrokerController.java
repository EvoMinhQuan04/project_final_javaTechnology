package com.example.bds.controller;

import com.example.bds.model.*;
import com.example.bds.model.Package;
import com.example.bds.repository.AvailableRepository;
import com.example.bds.repository.UserRepository;
import com.example.bds.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller()
public class BrokerController {
    @Autowired
    private PackageService packageService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private AvailableService availableService;


    @Autowired
    private UserService userService;

    @Autowired
    private DepositService depositService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  ImageLandService imageLandService;


    @Autowired
    private  ImageNewsService imageNewsService;

    @Autowired
    private AvailableRepository availableRepository;

    @Autowired
    private LandForSaleService landForSaleService;



    @GetMapping("/customer")
    public String customer(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_CUSTOMER"));


            if (isAdmin) {

                String address = userService.getAddress(username);
                String name = userService.getName(username);
                String email = userService.getEmail(username);
                String phone = userService.getPhone(username);
                model.addAttribute("email", email);
                model.addAttribute("phone", phone);
                model.addAttribute("name", name);

                model.addAttribute("username", username);
                model.addAttribute("role", "ROLE_CUSTOMER");
                model.addAttribute("address", address);
                Double money = userService.getMoney(username);
                model.addAttribute("money", money);

                return "dashboard_cus";
            }
        }
        return "redirect:/login";

    }



    @GetMapping("/customer-package")
    public String customer_package(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_CUSTOMER"));


            if (isAdmin) {

                String name = userService.getName(username);
                model.addAttribute("name", name);
                List<com.example.bds.model.Package> packageList = packageService.getAllPackage();


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:HH dd-MM-yyyy");
                List<com.example.bds.model.Package> filteredPackages = packageList.stream()
                        .filter(packagee -> packagee.getExpiry() != null && packagee.getExpiry().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toList());

                for (com.example.bds.model.Package packagee : filteredPackages) {
                    if (packagee.getExpiry() != null) {
                        String formattedExpiry = packagee.getExpiry().format(formatter);
                        packagee.setFormattedExpiry(formattedExpiry);
                    }
                }
                User u = userService.findByUser(username);


                List<Available> availableList = availableRepository.findByBroker(u);
                if (availableList.isEmpty()) {
                    System.out.println("Không có availableList nào.");
                }
                model.addAttribute("availableList", availableList);



                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("mm:HH dd-MM-yyyy");


                for (Available available : availableList) {

                    if (available.getPurchaseDate() != null) {
                        String formattedPurchase = available.getPurchaseDate().format(formatter1);
                        available.setFormattedPurchase(formattedPurchase);
                    }


                    if (available.getExpirationDate() != null) {
                        String formattedExpiry = available.getExpirationDate().format(formatter1);
                        available.setFormattedExpiry(formattedExpiry);
                    }
                }






                model.addAttribute("packageList", filteredPackages);

                Double money = userService.getMoney(username);
                model.addAttribute("money", money);
                if (model.containsAttribute("error")) {
                    System.out.println("Không đủ tiền");
                    model.addAttribute("error", model.getAttribute("error"));
                }
                return "dashboard_cus_package";
            }
        }
        return "redirect:/login";

    }

    @GetMapping("/customer-history")
    public String customer_history(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_CUSTOMER"));


            if (isAdmin) {

                String name = userService.getName(username);
                model.addAttribute("name", name);

                Long id = userService.getId(username);

                List<Deposit> depositList = depositService.getCustomerHistory(id);
                model.addAttribute("depositList", depositList);
                Double money = userService.getMoney(username);
                model.addAttribute("money", money);
                return "dashboard_cus_history";
            }
        }
        return "redirect:/login";

    }

    @PostMapping("/post")
    public ResponseEntity<String> postLand(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "price") Double price,
            @RequestParam(value = "area") Double area,
            @RequestParam(value = "city") String city,
            @RequestParam(value = "district") String district,
            @RequestParam(value = "ward") String ward,
            @RequestParam(value = "interior") String interior,
            @RequestParam(value = "numberOfToilets") Integer numberOfToilets,
            @RequestParam(value = "numberOfBedRooms") Integer numberOfBedRooms,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "type") String type,
            @RequestParam(value = "legal") String legal,
            @RequestParam(value = "propertyType") String propertyType,
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "availableId") Integer availableId,
            @RequestParam(value = "latitude") Double latitude,
            @RequestParam(value = "longitude") Double longitude,
            @RequestParam(value = "imageLinks", required = false) List<MultipartFile> imageLinks) {

        try {
            List<String> imageUrls = new ArrayList<>();
            if (imageLinks != null && !imageLinks.isEmpty()) {
                for (MultipartFile image : imageLinks) {
                    String imageUrl = saveImage(image);
                    imageUrls.add(imageUrl);
                }
            }


            LandForSale landForSale = new LandForSale();
            landForSale.setName(name);
            landForSale.setPrice(price);
            landForSale.setArea(area);
            landForSale.setProvince(city);
            landForSale.setDistrict(district);
            landForSale.setWard(ward);
            landForSale.setInterior(interior);
            landForSale.setNumberOfToilets(numberOfToilets);
            landForSale.setNumberOfBedRooms(numberOfBedRooms);
            landForSale.setDescription(description);
            landForSale.setDatePosted(LocalDateTime.now());
            landForSale.setType(type);
            landForSale.setLegal(legal);
            landForSale.setLatitude(latitude);
            landForSale.setLongitude(longitude);
            landForSale.setPropertyType(propertyType);


            landForSaleService.postLandForSale(landForSale, userId, availableId, imageUrls);

            return ResponseEntity.ok("Đă đăng tin");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Đă đăng tin");
        }
    }

    @PostMapping("/post-news")
    public ResponseEntity<String> postNews(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "summaryOfContent") String summaryOfContent,
            @RequestParam(value = "content") String content,
            @RequestParam(value = "userId1") Long userId,
            @RequestParam(value = "availableId1") Integer availableId,
            @RequestParam(value = "imageLinks", required = false) List<MultipartFile> imageLinks) {

        try {

            List<String> imageUrls = new ArrayList<>();
            if (imageLinks != null && !imageLinks.isEmpty()) {
                for (MultipartFile image : imageLinks) {
                    String imageUrl = saveImage(image);
                    imageUrls.add(imageUrl);
                }
            }


            News news = new News();
            news.setTitle(title);
            news.setContent(content);
            news.setSummaryOfContent(summaryOfContent);

            newsService.postedNews(news,userId,availableId,imageUrls);
            return ResponseEntity.ok("Đă đăng tin");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đă đăng tin");
        }
    }




    private String saveImage(MultipartFile file) throws IOException {

        String uploadDir = "src/main/resources/static/images";
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);


        Files.createDirectories(filePath.getParent());

        Files.write(filePath, file.getBytes());


        return "/images/" + fileName;
    }


    @PostMapping("/edit/{id}")
    public ResponseEntity<String> updateLand(
            @PathVariable("id") int id,
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("area") Double area,
            @RequestParam("interior") String interior,
            @RequestParam("numberOfToilets") Integer numberOfToilets,
            @RequestParam("numberOfBedRooms") Integer numberOfBedRooms,
            @RequestParam("description") String description,
            @RequestParam("legal") String legal) {

        try {

            LandForSale landForSale = landForSaleService.findById(id);


            landForSale.setName(name);
            landForSale.setPrice(price);
            landForSale.setArea(area);
            landForSale.setInterior(interior);
            landForSale.setNumberOfToilets(numberOfToilets);
            landForSale.setNumberOfBedRooms(numberOfBedRooms);
            landForSale.setDescription(description);
            landForSale.setLegal(legal);




            landForSaleService.updateLandForSale(id, landForSale);

            return ResponseEntity.ok("Update successful!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }




    @GetMapping("/edit/{id}")
    public String editLand(@PathVariable("id") int id, Model model) {
        LandForSale landForSale = landForSaleService.findById(id);
        model.addAttribute("landForSale", landForSale);
        return "dashboard_cus_sua";
    }


    @GetMapping("/delete/{id}")
    public String deleteLand(@PathVariable int id) {
        LandForSale land = landForSaleService.findById(id);
        imageLandService.deleteImageLandsByLandForSale(land);
        landForSaleService.deleteLandById(id);
        return "redirect:/customer-history-list";
    }

    @GetMapping("/delete/news/{id}")
    public String deleteNews(@PathVariable int id) {
        News land = newsService.findById(id);
        imageNewsService.deleteImageLandsByNews(land);
        newsService.deleteNews(id);
        return "redirect:/customer-history-list";
    }

    @GetMapping("/edit-news/{id}")
    public String editNews(@PathVariable int id, Model model) {
        News news = newsService.findById(id);
        model.addAttribute("news", news);
        return "dashboard_cus_edit_news";
    }

    @PostMapping("/edit-news/{id}")
    public String updateNews(@PathVariable int id,
                            @RequestParam String title,
                            @RequestParam String summaryOfContent,
                            @RequestParam String content,
                            RedirectAttributes ra) {
        News news = newsService.findById(id);
        news.setTitle(title);
        news.setSummaryOfContent(summaryOfContent);
        news.setContent(content);
        newsService.updateNews(id, news);
        ra.addFlashAttribute("message", "Cập nhật tin tức thành công");
        return "redirect:/customer-history-list";
    }

    




    @PostMapping("/customer-package/purchase")
    public String purchasePackage(@RequestParam("selectedPackage") Integer selectedPackage, Model model, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_CUSTOMER"));


            if (isAdmin) {
                User user = userService.findByUser(username);
                List<com.example.bds.model.Package> packages = packageService.getAllPackage();
                Package selected = packageService.getPackageById(selectedPackage).orElseThrow(() -> new IllegalArgumentException("Invalid package Id"));
                Double totalAmount = selected.getPrice();


                Double money = userService.getMoney(username);
                model.addAttribute("money", money);
                if (money < totalAmount) {
                    redirectAttributes.addFlashAttribute("error", "Bạn chưa đủ tiền để mua gói tin!");
                    return "redirect:/customer-package";
                }
                userService.minusMoney(username,totalAmount);
                System.out.println("Yes");

                Available available = new Available();
                available.setStatusPayment("Paid");
                available.setPurchaseDate(LocalDateTime.now());
                available.setQuantityAvailable(selected.getQuantity());
                available.setTotal(selected.getPrice());
                available.setExpirationDate(selected.getExpiry());
                available.setBroker(user);
                available.setPackagee(selected);


                availableRepository.save(available);

                return "redirect:/customer-package";


            }
        }
        return "redirect:/login";

    }

    @GetMapping("/customer-dangtin")
    public String dangtin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_CUSTOMER"));


            if (isAdmin) {

                String name = userService.getName(username);
                model.addAttribute("name", name);

                User user = userService.findByUser(username);
                Long userId = user.getId();
                List<Available> availableList = availableRepository.findByBrokerAndQuantityAvailableGreaterThanAndExpirationDateAfter(user,0,LocalDateTime.now());


                if (availableList.isEmpty()) {
                    System.out.println("Không có availableList nào.");
                }


                model.addAttribute("availableList", availableList);
                model.addAttribute("userId", userId);


                return "dashboard_cus_dangtin";
            }
        }
        return "redirect:/login";

    }


    @GetMapping("/customer-history-list")
    public String customer_history_list(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_CUSTOMER"));


            if (isAdmin) {

                Long id = userService.getId(username);
                String name = userService.getName(username);
                model.addAttribute("name", name);

                List<LandForSale> landForSales = landForSaleService.listLandByBrokerSold(id);
                List<LandForSale> landForSales1 = landForSaleService.listLandByBrokerRent(id);
                List<News> news = newsService.listLandByBroker(id);

                System.out.println(news.size());

                model.addAttribute("news", news);

                model.addAttribute("landForSales", landForSales);
                model.addAttribute("landForRents", landForSales1);
                model.addAttribute("id", id);
                Double money = userService.getMoney(username);
                model.addAttribute("money", money);


                return "dashboard_cus_list";
            }
        }
        return "redirect:/login";

    }



    @GetMapping("/customer-account")
    public String customer_account(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_CUSTOMER"));


            if (isAdmin) {

                Long id = userService.getId(username);
                User u = userService.findByUser(username);
                Double money = userService.getMoney(username);
                String name = userService.getName(username);


                model.addAttribute("user", u);
                model.addAttribute("name", name);
                model.addAttribute("id", id);
                model.addAttribute("money", money);


                return "dashboard_cus_account";
            }
        }
        return "redirect:/login";

    }



    @PostMapping("/customer-account/update")
    public String updateCustomerAccount(@ModelAttribute User updatedUser, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            User existingUser = userService.findByUser(username);
            if (existingUser != null) {
                existingUser.setZoneking(updatedUser.getZoneking());
                existingUser.setName(updatedUser.getName());
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setPhone(updatedUser.getPhone());
                existingUser.setAddress(updatedUser.getAddress());


                userRepository.save(existingUser);

                model.addAttribute("message", "Account updated successfully!");
                model.addAttribute("user", existingUser);
            }
        }
        return "redirect:/customer-account";

    }


    @GetMapping("/customer-deposit")
    public String customer_deposit(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_CUSTOMER"));


            if (isAdmin) {

                Long id = userService.getId(username);
                String name = userService.getName(username);
                model.addAttribute("name", name);
                List<Deposit> depositList = depositService.getCustomerHistory(id);
                model.addAttribute("depositList", depositList);
                model.addAttribute("id", id);
                Double money = userService.getMoney(username);
                model.addAttribute("money", money);


                return "dashboard_cus_deposit";
            }
        }
        return "redirect:/login";

    }

    @GetMapping("/customer-change")
    public String customer_change(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_CUSTOMER")  );


            if (isAdmin) {

                Long id = userService.getId(username);
                String name = userService.getName(username);

                model.addAttribute("name", name);
                model.addAttribute("id", id);
                Double money = userService.getMoney(username);
                model.addAttribute("money", money);




                return "dashboard_cus_change";
            }
        }
        return "redirect:/login";

    }




    @PostMapping("/customer-change-password")
    public String changePassword1(
            @RequestParam("id") Long userId,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {


        User user = userService.findById(userId);
        System.out.println(userId);
        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "dashboard_cus_change";
        }


        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            model.addAttribute("error", "Mật khẩu hiện tại không đúng.");
            return "dashboard_cus_change";
        }


        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu mới không khớp.");
            return "dashboard_cus_change";
        }


        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        model.addAttribute("message", "Đổi mật khẩu thành công.");
        return "redirect:/customer-change";

    }
}
