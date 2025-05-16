package com.example.pay.controller;

import com.example.pay.model.Deposit;
import com.example.pay.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/deposits")
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

    @GetMapping("/user/{userId}")
    public List<Deposit> getDepositHistory(@PathVariable Long userId) {
        return depositService.getDepositsByUserId(userId);
    }


}
