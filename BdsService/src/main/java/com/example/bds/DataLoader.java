package com.example.bds;

import com.example.bds.model.Role;
import com.example.bds.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            // Kiểm tra và thêm vai trò ADMIN
            if (roleRepository.findByRoleName("ROLE_ADMIN") == null) {
                roleRepository.save(new Role("ROLE_ADMIN"));
            }

            // Kiểm tra và thêm vai trò CUSTOMER
            if (roleRepository.findByRoleName("ROLE_CUSTOMER") == null) {
                roleRepository.save(new Role("ROLE_CUSTOMER"));
            }
        };
    }
}
