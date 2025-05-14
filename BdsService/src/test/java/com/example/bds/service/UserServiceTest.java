
package com.example.bds.service;

import com.example.bds.model.Role;
import com.example.bds.model.User;
import com.example.bds.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testAuthenticateUser_Success() {
        User user = new User("testUser", "encodedPassword");
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(passwordEncoder.matches("rawPassword", "encodedPassword")).thenReturn(true);

        boolean result = userService.authenticateUser("testUser", "rawPassword");

        assertTrue(result);
        verify(userRepository, times(1)).findByUsername("testUser");
        verify(passwordEncoder, times(1)).matches("rawPassword", "encodedPassword");
    }

    @Test
    void testAuthenticateUser_Failure() {
        when(userRepository.findByUsername("testUser")).thenReturn(null);

        boolean result = userService.authenticateUser("testUser", "rawPassword");

        assertFalse(result);
        verify(userRepository, times(1)).findByUsername("testUser");
        verifyNoInteractions(passwordEncoder);
    }
    @Test
    void testRegisterUser_Success() {
        User user = new User("newUser", "rawPassword");

        when(userRepository.findByUsername("newUser")).thenReturn(null);

        when(userRepository.save(user)).thenReturn(user);

        boolean result = userService.registerUser(user, "ROLE_USER");

        assertTrue(result);

        assertNotEquals("rawPassword", user.getPassword());
        assertTrue(passwordEncoder.matches("rawPassword", user.getPassword()));

        verify(userRepository, times(1)).findByUsername("newUser");
        verify(userRepository, times(1)).save(user);
    }


    @Test
    void testRegisterUser_Failure() {
        when(userRepository.findByUsername("existingUser")).thenReturn(new User());

        boolean result = userService.registerUser(new User("existingUser", "password"), "ROLE_USER");

        assertFalse(result);
        verify(userRepository, times(1)).findByUsername("existingUser");
        verifyNoInteractions(passwordEncoder);
    }

    @Test
    void testGetUserRole() {
        User user = new User();
        user.setUsername("testUser");
        Role role = new Role();
        role.setRoleName("ROLE_ADMIN");
        user.setRoles(Set.of(role));
        when(userRepository.findByUsername("testUser")).thenReturn(user);

        String roleName = userService.getUserRole("testUser");

        assertEquals("ROLE_ADMIN", roleName);
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void testGetId() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findByUsername("testUser")).thenReturn(user);

        Long id = userService.getId("testUser");

        assertEquals(1L, id);
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void testUpdatePassword() {
        User user = new User("testUser", "oldPassword");
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        String newPassword = "newPassword";
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedPassword");

        userService.updatePassword("test@example.com", newPassword);

        assertEquals("encodedPassword", user.getPassword());
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(passwordEncoder, times(1)).encode(newPassword);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testIsEmailRegistered_True() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(new User());

        boolean result = userService.isEmailRegistered("test@example.com");

        assertTrue(result);
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    void testIsEmailRegistered_False() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        boolean result = userService.isEmailRegistered("nonexistent@example.com");

        assertFalse(result);
        verify(userRepository, times(1)).findByEmail("nonexistent@example.com");
    }
}



