package com.example.bds.service;

import com.example.bds.repository.AvailableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.*;

@Service
public class StatisticsService {

    @Autowired
    private AvailableRepository availableRepository;

    public List<Map<String, Object>> getPackageStatistics() {
        List<Object[]> stats = availableRepository.getPackageStatistics();
        List<Map<String, Object>> statistics = new ArrayList<>();

        for (Object[] row : stats) {
            Map<String, Object> data = new HashMap<>();
            data.put("packageName", row[0]);  // Tên gói
            data.put("purchaseCount", row[1]);  // Số lần mua
            data.put("totalRevenue", row[2]);  // Tổng doanh thu
            statistics.add(data);
        }

        return statistics;
    }
}
