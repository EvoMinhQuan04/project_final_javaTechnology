


package com.example.bds.service;

import com.example.bds.model.Role;
import com.example.bds.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;



    @Test
    void testFindByName_Success() {
        Role role = new Role("ROLE_USER");
        when(roleRepository.findByRoleName("ROLE_USER")).thenReturn(role);

        Role foundRole = roleService.findByName("ROLE_USER");

        assertNotNull(foundRole);
        assertEquals("ROLE_USER", foundRole.getRoleName());
        verify(roleRepository, times(1)).findByRoleName("ROLE_USER");
    }

    @Test
    void testFindByName_NotFound() {
        when(roleRepository.findByRoleName("ROLE_GUEST")).thenReturn(null);

        Role foundRole = roleService.findByName("ROLE_GUEST");

        assertNull(foundRole);
        verify(roleRepository, times(1)).findByRoleName("ROLE_GUEST");
    }
}
