package com.example.bds.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class DoanhNghiepController {
    @GetMapping("/doanh-nghiep")
    public String doanhnghiep() {
        return "doanh_nghiep";
    }

    @GetMapping("/chi-tiet-doanh-nghiep")
    public String detaildoanhnghiep() {
        return "detail/detail_doanh_nghiep";
    }
}
