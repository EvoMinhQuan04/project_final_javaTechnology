package com.example.bds.service;

import com.example.bds.model.User;
import com.example.bds.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public boolean authenticateUser(String username, String password) {

        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return true;
        }
        return false;
    }

    public UserService() {
    }

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean registerUser(User user, String roleName) {

        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return true;
    }


    public String getUserRole(String username) {

        User user = userRepository.findByUsername(username);
        if (user != null && !user.getRoles().isEmpty()) {

            return user.getRoles().iterator().next().getRoleName();
        }
        return null;
    }



    public Long getId(String username) {
        // Logic lấy email của người dùng
        User user = userRepository.findByUsername(username);
        return user.getId();
    }

    public String getAddress(String username) {
        // Logic lấy email của người dùng
        User user = userRepository.findByUsername(username);
        return user.getAddress();
    }

    public String getName(String username) {
        User user = userRepository.findByUsername(username);
        return user.getName();
    }

    public String getEmail(String username){
        User user = userRepository.findByUsername(username);
        return user.getEmail();
    }

    public String getPhone(String username){
        User user = userRepository.findByUsername(username);
        return user.getPhone();
    }

    


    public Double getMoney(String username) {
        User user = userRepository.findByUsername(username);
        return user.getBalance();
    }

    public List<User> getListUser() {
        return userRepository.findAll();
    }

    public List<User> getCustomers() {
        return userRepository.findCustomers();
    }
    public User findByUser(String username){
        return userRepository.findByUsername(username);
    }

    public User findById(Long id){
        return userRepository.findById(id).get();
    }


    public void minusMoney(String username,Double newMon) {
        User user = userRepository.findByUsername(username);
        Double money =  user.getBalance();
        user.setBalance(money-newMon);
    }



    public boolean isEmailRegistered(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public void updatePassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email);  // Tìm người dùng theo email
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);  // Lưu người dùng với mật khẩu mới
        }
    }






}
