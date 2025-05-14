



package com.example.bds.service;

import com.example.bds.model.Package;
import com.example.bds.repository.PackageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackageServiceTest {

    @Mock
    private PackageRepository packageRepository;

    @InjectMocks
    private PackageService packageService;

    @Test
    void testCreatePackage() {
        Package packagee = new Package("Basic Package", 100.0, 10, LocalDateTime.now());
        when(packageRepository.save(packagee)).thenReturn(packagee);

        Package result = packageService.createPackage(packagee);

        assertNotNull(result);
        assertEquals("Basic Package", result.getName());
        verify(packageRepository, times(1)).save(packagee);
    }

    @Test
    void testGetPackageById() {
        Package packagee = new Package("Basic Package", 100.0, 10, LocalDateTime.now());
        packagee.setId(1);
        when(packageRepository.findById(1)).thenReturn(Optional.of(packagee));

        Optional<Package> result = packageService.getPackageById(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
        verify(packageRepository, times(1)).findById(1);
    }

    @Test
    void testGetAllPackages() {
        Package package1 = new Package("Package 1", 100.0, 10, LocalDateTime.now());
        Package package2 = new Package("Package 2", 200.0, 20, LocalDateTime.now());
        when(packageRepository.findAll()).thenReturn(Arrays.asList(package1, package2));

        Iterable<Package> result = packageService.getAllPackages();

        assertNotNull(result);
        assertEquals(2, ((List<Package>) result).size());
        verify(packageRepository, times(1)).findAll();
    }

    @Test
    void testUpdatePackage() {
        Package existingPackage = new Package("Old Package", 100.0, 10, LocalDateTime.now());
        existingPackage.setId(1);

        Package updatedPackage = new Package("Updated Package", 200.0, 20, LocalDateTime.now());

        when(packageRepository.findById(1)).thenReturn(Optional.of(existingPackage));
        when(packageRepository.save(existingPackage)).thenReturn(existingPackage);

        Package result = packageService.updatePackage(1, updatedPackage);

        assertNotNull(result);
        verify(packageRepository, times(1)).findById(1);
        verify(packageRepository, times(1)).save(existingPackage);
    }

    @Test
    void testDeletePackage() {
        packageService.deletePackage(1);

        verify(packageRepository, times(1)).deleteById(1);
    }

    @Test
    void testGetAllPackageList() {
        Package package1 = new Package("Package 1", 100.0, 10, LocalDateTime.now());
        Package package2 = new Package("Package 2", 200.0, 20, LocalDateTime.now());
        when(packageRepository.findAll()).thenReturn(Arrays.asList(package1, package2));

        List<Package> result = packageService.getAllPackage();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(packageRepository, times(1)).findAll();
    }
}