package com.example.bds.controller;

import com.example.bds.service.CheckOutService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class CheckOutController {

    private final CheckOutService checkOutService;

    public CheckOutController(CheckOutService checkOutService) {
        this.checkOutService = checkOutService;
    }

    @PostMapping(value = "/checkout")
    public String checkOut(@RequestParam("money") int money, @RequestParam("userId") Long userId, HttpServletRequest request) {
        // Tạo URL cơ sở để chuyển hướng sau thanh toán
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        // Gọi service để tạo URL thanh toán
        System.out.println(userId);
        String vnpayUrl = checkOutService.checkOutWithPayOnline(money, baseUrl, userId);

        System.out.println("vnpayUrl: " + vnpayUrl);
        return "redirect:" + vnpayUrl; // Chuyển hướng tới VNPAY
    }


    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model) {
        // Kiểm tra trạng thái thanh toán
        int paymentStatus = checkOutService.orderReturn(request);

        // Lấy thông tin từ request trả về
        String userId = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        // Lấy userId từ tham số request (được truyền từ VNPAY trong URL)

        // Chuyển đổi totalPrice về kiểu Double, vì API depositMoney yêu cầu Double
        Double amount = Double.valueOf(totalPrice) / 100; // VNPAY gửi giá trị dưới dạng cents, cần chia cho 100

        // Lưu các thông tin vào model để hiển thị trên giao diện


        System.out.println("Tiền: "+amount);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);
        model.addAttribute("userId", userId);  // Thêm userId vào model nếu cần hiển thị

        if (paymentStatus == 1) {
            // Nếu thanh toán thành công, gọi API depositMoney
            try {





                // Tạo RestTemplate để gọi API
                RestTemplate restTemplate = new RestTemplate();
                String apiUrl = "http://localhost:8080/customer/deposits/user/" + userId;  // Sử dụng userId thay vì orderInfo

                // Gửi yêu cầu POST đến API depositMoney
                HttpEntity<Object> requestEntity = new HttpEntity<>(null);
                ResponseEntity<String> response = restTemplate.exchange(apiUrl + "?amount=" + amount, HttpMethod.POST, requestEntity, String.class);

                // Kiểm tra kết quả trả về từ API
                if (response.getStatusCode().is2xxSuccessful()) {
                    System.out.println("Successfully deposited money for user: " + userId);
                } else {
                    System.out.println("Failed to deposit money.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "error";  // Có thể trả về trang lỗi nếu gặp sự cố
            }
        }

        return "redirect:/customer-deposit";
    }

}