package com.example.bds.controller;

import com.example.bds.model.User;
import com.example.bds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller()
public class NhaMoiGioiController {
    @Autowired
    private UserService userService;


    @GetMapping("/nha-moi-gioi")
    public String nhamoigioi(Model model) {

        List<User> users = userService.getCustomers();
        for(User u : users){
            System.out.println(u.toString());
        }
        model.addAttribute("users", users);

        return "nha_moi_gioi";
    }

    @GetMapping("/chi-tiet-nha-moi-gioi")
    public String detailnhamoigioi() {
        return "detail/detail_nha_moi_gioi";
    }
}
