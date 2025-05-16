package com.example.pay.service;

import com.example.pay.model.Deposit;
import com.example.pay.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
@Service
public class DepositService {

    @Autowired
    private DepositRepository depositRepository;

    public Deposit createDeposit(Long userId, Double amount) throws Exception {
        if (amount <= 0) {
            throw new Exception("Invalid deposit amount");
        }

        Deposit deposit = new Deposit();
        deposit.setSoTien(amount);
        deposit.setNgayNap(LocalDateTime.now());
        deposit.setTinhTrangThanhToan("Đã thanh toán");
        deposit.setUserId(userId);

        return depositRepository.save(deposit);
    }

    public List<Deposit> getDepositsByUserId(Long userId) {
        return depositRepository.findByUserId(userId);
    }
}
