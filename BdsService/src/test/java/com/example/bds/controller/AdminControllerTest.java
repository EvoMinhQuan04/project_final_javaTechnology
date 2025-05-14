
package com.example.bds.controller;
import com.example.bds.model.Available;
import com.example.bds.model.User;
import com.example.bds.service.AvailableService;
import com.example.bds.service.UserService;
import com.example.bds.service.PackageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.*;
@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private PackageService packageService;


    @Mock
    private AvailableService availableService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHome() throws Exception {
        when(userService.getName("caoky")).thenReturn("Admin User");
        when(userService.getAddress("caoky")).thenReturn("Admin Address");

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"));
    }

    @Test
    void testManagerCustomer() throws Exception {
        when(userService.getName("caoky")).thenReturn("Admin User");
        when(userService.getListUser()).thenReturn(List.of(new User("customer1"), new User("customer2")));

        mockMvc.perform(get("/manager-customer"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard_manager_customer"));
    }

    @Test
    void testManagerPackage() throws Exception {
        when(userService.getName("caoky")).thenReturn("Admin User");
        when(packageService.getAllPackage()).thenReturn(List.of(new com.example.bds.model.Package(), new com.example.bds.model.Package()));

        mockMvc.perform(get("/manager-package"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard_manager_package"));
    }

    @Test
    void testDeleteLand() throws Exception {
        mockMvc.perform(get("/admin/delete/24"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/manager-list"));
    }

    @Test
    void testManagerHistory() throws Exception {
        when(userService.getName("caoky")).thenReturn("Admin User");
        when(availableService.findAllAvailable()).thenReturn(List.of(new Available(), new Available()));

        mockMvc.perform(get("/manager-history"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard_manager_history"));
    }

    @Test
    void testChangePassword() throws Exception {
        User user = new User();
        user.setPassword("currentPassword");

        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/admin-change-password")
                        .param("id", "1")
                        .param("currentPassword", "currentPassword")
                        .param("newPassword", "newPassword")
                        .param("confirmPassword", "newPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin-change"));
    }
}


