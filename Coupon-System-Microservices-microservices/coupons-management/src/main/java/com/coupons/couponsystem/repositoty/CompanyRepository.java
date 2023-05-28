package com.coupons.couponsystem.repositoty;

import com.coupons.couponsystem.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {




    Optional<Company> findByUserId(Long userId);

    Optional<List<Company>> findAllByIsActiveTrue();


    @Query("select c from Company c join fetch c.coupons where c.id = :id")
    Optional<Company> findFullCompany(Long id);

    boolean existsByName(String name);

}
