package com.example.bds.service;

import com.example.bds.repository.PackageRepository;
import com.example.bds.model.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PackageService {
    @Autowired
    private PackageRepository packageRepository;


    public Package createPackage(Package packagee) {
        return packageRepository.save(packagee);
    }


    public Optional<Package> getPackageById(int id) {
        return packageRepository.findById(id);
    }

    public Iterable<Package> getAllPackages() {
        return packageRepository.findAll();
    }


    public Package updatePackage(int id, Package updatedPackage) {
        return packageRepository.findById(id)
                .map(existingPackage -> {

                    return packageRepository.save(existingPackage);
                }).orElseThrow(() -> new RuntimeException("Package not found"));
    }


    public void deletePackage(int id) {
        packageRepository.deleteById(id);
    }


    public List<Package> getAllPackage(){
        return packageRepository.findAll();
    }
}

