package com.coupons.couponsystem.repositoty;


import com.coupons.couponsystem.model.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageData,Long> {


    Optional<ImageData> findByName(String fileName);
}