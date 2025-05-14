package com.example.bds.repository;

import com.example.bds.model.LandForSale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;

public interface LandForSaleRepository extends JpaRepository<LandForSale, Integer> {
    List<LandForSale> findByBrokerId(Long brokerId);
    List<LandForSale> findByTypeIn(List<String> types);

    Page<LandForSale> findByTypeIn(List<String> types, Pageable pageable);

    List<LandForSale> findByBrokerIdAndTypeIn(Long userId, List<String> types);

    Page<LandForSale> findByTypeInAndProvince(List<String> types, String province, Pageable pageable);

    @Query("SELECT l FROM LandForSale l WHERE " +
            "(COALESCE(:propertyTypes, null) IS NULL OR l.propertyType IN :propertyTypes) " +
            "AND l.price BETWEEN :minPrice AND :maxPrice " +
            "AND (COALESCE(:landTypes, null) IS NULL OR l.type IN :landTypes) " +
            "AND l.area BETWEEN :minArea AND :maxArea " +
            "AND (COALESCE(:numberOfBedRooms, 0) = 0 OR " +
            "(:numberOfBedRooms = 5 AND l.numberOfBedRooms >= :numberOfBedRooms) OR " +
            "(l.numberOfBedRooms = :numberOfBedRooms)) " +
            "AND (COALESCE(:province, '') = '' OR LOWER(l.province) LIKE LOWER(CONCAT('%', :province, '%'))) " +
            "AND (COALESCE(:district, '') = '' OR LOWER(l.district) LIKE LOWER(CONCAT('%', :district, '%')))")
    Page<LandForSale> findByPropertyTypesAndPriceAndTypesAndAreaAndNumberOfBedRoomsAndLocation(
            List<String> propertyTypes,
            BigInteger minPrice,
            BigInteger maxPrice,
            List<String> landTypes,
            int minArea,
            int maxArea,
            int numberOfBedRooms,
            String province,
            String district,
            Pageable pageable);

    @Query("SELECT l FROM LandForSale l WHERE l.province LIKE %:province% " +
            "AND (COALESCE(:landTypes, null) IS NULL OR l.type IN :landTypes)")
    List<LandForSale> findByProvinceLike(@Param("province") String province,
                                         @Param("landTypes") List<String> landTypes);

}
