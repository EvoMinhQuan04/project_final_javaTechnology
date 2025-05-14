package com.example.bds.repository;

import com.example.bds.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;
public interface DepositRepository extends JpaRepository<Deposit, Integer> {
    List<Deposit> findByUserId(Long userId);
}
