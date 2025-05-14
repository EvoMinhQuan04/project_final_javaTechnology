package com.example.bds.controller;

import com.example.bds.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/deposits")
public class DepositController {

    @Autowired
    private DepositService depositService;

    @PostMapping("/user/{userId}")
    public String depositMoney(
            @PathVariable Long userId,
            @RequestParam Double amount) {
        try {
            depositService.createDeposit(userId, amount);
            return "Success";
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
