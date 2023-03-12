package com.couponsystempurchasestats.couponsystempurchasestats.repository;

import com.couponsystempurchasestats.couponsystempurchasestats.entity.PurchaseStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseStatsRepository extends JpaRepository<PurchaseStats,Long> {

    //get all
    Optional<List<PurchaseStats>> findAllByCustomerIdOrderByIdDesc(long customerId);
    Optional<List<PurchaseStats>> findAllByCompanyIdOrderByIdDesc(long companyId);
    Optional<List<PurchaseStats>> findTop5AllByCustomerIdOrderByIdDesc(long customerId);
    Optional<List<PurchaseStats>> findTop5AllByCompanyIdOrderByIdDesc(long companyId);
    List<PurchaseStats> findTop5AllByOrderByIdDesc();



}
