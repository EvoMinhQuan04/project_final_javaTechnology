package com.example.bds.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class DuAnBatDongSanController {
    @GetMapping("/du-an-bat-dong-san")
    public String duanbatdongsan() {
        return "du_an";
    }

    @GetMapping("/chi-tiet-du-an-bat-dong-san")
    public String detailduanbatdongsan() {
        return "detail/detail_du_an";
    }
}
