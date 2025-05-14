package com.example.bds.service;

import com.example.bds.model.Deposit;
import com.example.bds.model.User;
import com.example.bds.repository.DepositRepository;
import com.example.bds.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class DepositService {
    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private UserRepository userRepository;

    public Deposit saveDeposit(Deposit deposit) {
        return depositRepository.save(deposit);
    }

    public Iterable<Deposit> getAllDeposits() {
        return depositRepository.findAll();
    }

    public void deleteDeposit(Deposit deposit) {
        depositRepository.delete(deposit);
    }

    public Deposit update(int id, Deposit updatedDeposit) {
        Optional<Deposit> existingDeposit = depositRepository.findById(id);
        if (existingDeposit.isPresent()) {
            Deposit deposit = existingDeposit.get();
            deposit.setTinhTrangThanhToan(updatedDeposit.getTinhTrangThanhToan());
            return depositRepository.save(deposit);
        }
        else {
            throw new RuntimeException("Deposit not found");
        }
    }


    @Autowired
    private RestTemplate restTemplate;

    private static final String DEPOSIT_SERVICE_URL = "http://localhost:8081/deposits/user/";

    public void createDeposit(Long userId, Double amount) throws Exception {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));


        if (amount <= 0) {
            throw new Exception("Invalid deposit amount");
        }


        user.setBalance((user.getBalance() != null ? user.getBalance() : 0) + amount);
        userRepository.save(user);

        String url = DEPOSIT_SERVICE_URL + userId + "?amount=" + amount;
        restTemplate.postForObject(url, null, String.class);
//
//        Deposit deposit = new Deposit();
//        deposit.setSoTien(amount);
//        deposit.setNgayNap(LocalDateTime.now());
//        deposit.setTinhTrangThanhToan("Đã thanh toán");
//        deposit.setUser(user);


//        depositRepository.save(deposit);
    }


    public List<Deposit> getCustomerHistory(Long userId) {
        String url = DEPOSIT_SERVICE_URL + userId;

        ResponseEntity<List<Deposit>> response = restTemplate.exchange(
                url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Deposit>>() {}
        );

        return response.getBody();
    }

    public List<Deposit> customer_history(Long userId) {
        return depositRepository.findByUserId(userId);
    }

}
