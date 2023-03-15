package com.couponsystempurchasestats.couponsystempurchasestats.repository;

import com.couponsystempurchasestats.couponsystempurchasestats.entity.PurchaseStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseStatsRepository extends JpaRepository<PurchaseStats,Long> {

    //private long id;
    //    private long product_id;
    //    private long customer_id;
    //    private long company_id;
    //    private double price;
    //    private LocalDateTime dateOfPurchase;
    Optional<List<PurchaseStats>> findAllByCustomerIdOrderByIdDesc(long customerId);
    Optional<List<PurchaseStats>> findAllByCompanyId(long companyId);
    //IdOrderByIdDesc
    Optional<List<PurchaseStats>> findTop5AllByCustomerIdOrderByIdDesc(long customerId);
    Optional<List<PurchaseStats>> findTop5AllByCompanyIdOrderByIdDesc(long companyId);
    List<PurchaseStats> findTop5AllByOrderByIdDesc();

}
