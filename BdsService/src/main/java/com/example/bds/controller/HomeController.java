package com.example.bds.controller;

import com.example.bds.dto.UserRegistrationRequest;
import com.example.bds.model.*;
import com.example.bds.security.JwtTokenUtil;
import com.example.bds.service.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.SecureRandom;
import java.time.format.DateTimeFormatter;
import java.util.*;
@Controller()
public class HomeController {
    @Autowired
    private NewsService newsService;

    @Autowired
    private LandForSaleService landForSaleService;

    @Autowired
    private UserService userService;

    @Autowired
    private DepositService depositService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/")
    public String home(Model model) {

        List<LandForSale> landForSaleList = landForSaleService.listLandSold();

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

        model.addAttribute("landForSaleList", landForSaleList);

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
        List<News> news2 = newsService.listLandTop4();

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");


        News latestNews = newsService.getLatestNews();


        if (latestNews != null && latestNews.getPublishDate() != null) {
            String formattedDate = latestNews.getPublishDate().format(formatter2);
            latestNews.setFormattedExpiry(formattedDate);
//            model.addAttribute("latestNews", latestNews);
        }

        model.addAttribute("latestNews", latestNews);



        model.addAttribute("top4", news2);


        return "home";
    }

    @GetMapping("/dang-tin")
    public String dangtin() {
        return "dashboardAccount";
    }

    @GetMapping("/chitiet-tin-tuc/{id}")
    public String chitiettintuc(@PathVariable("id") int id, Model model) {
        News newsDetail = newsService.getNewsById(id); // Tìm tin tức theo ID
        if (newsDetail == null) {
            return "redirect:/tin-tuc"; // Quay lại danh sách tin tức nếu không tìm thấy
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        if (newsDetail.getPublishDate() != null) {
            String formattedDate = newsDetail.getPublishDate().format(formatter);
            newsDetail.setFormattedExpiry(formattedDate);
        }

        List<News> news = newsService.listLand();

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        Collections.sort(news, new Comparator<News>() {
            @Override
            public int compare(News n1, News n2) {
                if (n1.getPublishDate() == null || n2.getPublishDate() == null) {
                    return 0;
                }
                return n1.getPublishDate().compareTo(n2.getPublishDate());
            }
        });

        Collections.sort(news, (n1, n2) -> {
            if (n1.getPublishDate() == null || n2.getPublishDate() == null) {
                return 0;
            }
            return n2.getPublishDate().compareTo(n1.getPublishDate());
        });


        for (News news1 : news) {
            if (news1.getPublishDate() != null) {
                String formattedPurchase = news1.getPublishDate().format(formatter1);
                news1.setFormattedExpiry(formattedPurchase);
            }
        }


        model.addAttribute("newsDetail", newsDetail); // Thêm tin tức vào model
        model.addAttribute("news", news);

        return "chitiet_tin_tuc"; // Trả về trang chi tiết tin tức
    }


    @GetMapping("/tin-tuc")
    public String tintuc(Model model) {
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

        List<News> news = newsService.listLand();
        List<News> news2 = newsService.listLandTop4();

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        Collections.sort(news, new Comparator<News>() {
            @Override
            public int compare(News n1, News n2) {
                if (n1.getPublishDate() == null || n2.getPublishDate() == null) {
                    return 0;
                }
                return n1.getPublishDate().compareTo(n2.getPublishDate());
            }
        });

        Collections.sort(news, (n1, n2) -> {
            if (n1.getPublishDate() == null || n2.getPublishDate() == null) {
                return 0;
            }
            return n2.getPublishDate().compareTo(n1.getPublishDate());
        });


        for (News news1 : news) {
            if (news1.getPublishDate() != null) {
                String formattedPurchase = news1.getPublishDate().format(formatter1);
                news1.setFormattedExpiry(formattedPurchase);
            }
        }

        News latestNews = newsService.getLatestNews();


        if (latestNews != null && latestNews.getPublishDate() != null) {
            String formattedDate = latestNews.getPublishDate().format(formatter2);
            latestNews.setFormattedExpiry(formattedDate); // Giả sử bạn có phương thức setFormattedExpiry
        }

        model.addAttribute("latestNews", latestNews);



        model.addAttribute("news", news);
        model.addAttribute("top4", news2);

        return "tin_tuc";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/forgot")
    public String forgot() {
        return "forgot";
    }


    @PostMapping("/forgot")
    public ResponseEntity<String> sendResetLink(@RequestBody Map<String, String> requestData, HttpServletResponse response) {
        String email = requestData.get("email");

        if (userService.isEmailRegistered(email)) {
            String newPass = PasswordUtil.generateRandomPassword(6);  // độ dài 6 ký tự
            userService.updatePassword(email, newPass);
            emailService.sendPasswordResetEmail(email, newPass);
            return ResponseEntity.ok("Khôi phục thành công");
        } else {
            return ResponseEntity.badRequest().body("Thất bại");
        }
    }

    //bổ trợ random pass
    public class PasswordUtil {
        private static final String CHARACTERS =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                        "abcdefghijklmnopqrstuvwxyz" +
                        "0123456789";
        private static final SecureRandom random = new SecureRandom();

        public static String generateRandomPassword(int length) {
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int idx = random.nextInt(CHARACTERS.length());
                sb.append(CHARACTERS.charAt(idx));
            }
            return sb.toString();
        }
    }





    @GetMapping("/register")
    public String register() {
        return "register";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }


        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpServletResponse response) {
        boolean authenticated = userService.authenticateUser(username, password);
        if (authenticated) {
            try {
                String role = userService.getUserRole(username);
                String token = JwtTokenUtil.createToken(username, role);
                model.addAttribute("token", token);


                Cookie tokenCookie = new Cookie("token", token);
                tokenCookie.setHttpOnly(true);
                System.out.println("Token: " + token);
                System.out.println("Is token valid: " + JwtTokenUtil.verifyToken(token));
                System.out.println("Authentication: " + SecurityContextHolder.getContext().getAuthentication());

                tokenCookie.setPath("/");
                response.addCookie(tokenCookie);
                if ("ROLE_ADMIN".equalsIgnoreCase(role)) {
                    return "redirect:/admin";
                } else if ("ROLE_CUSTOMER".equalsIgnoreCase(role)) {
                    return "redirect:/customer";
                } else {
                    model.addAttribute("error", "Invalid role");
                    return "login";
                }
            } catch (Exception e) {
                model.addAttribute("error", "Error creating token");
                return "login";
            }
        } else {
            model.addAttribute("error", "Không tồn tại tài khoản");
            return "login";
        }
    }



    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest userRequest) {

        Role role = roleService.findByName(userRequest.getRoleName());
        if (role == null) {
            return ResponseEntity.status(400).body("Role does not exist");
        }

        User user = new User(userRequest.getUsername(), userRequest.getPassword(),userRequest.getName(),userRequest.getAddress(),userRequest.getZoneking(),userRequest.getEmail(),userRequest.getPhone());
        user.getRoles().add(role);

        boolean result = userService.registerUser(user, userRequest.getRoleName());

        if (result) {
            return ResponseEntity.status(201).body("User registered successfully with role: " + userRequest.getRoleName());
        } else {
            return ResponseEntity.status(500).body("Failed to register user");
        }
    }

}
