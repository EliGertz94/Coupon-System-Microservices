package com.userstats.repository;


import com.userstats.entity.UserStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserStatsRepository extends JpaRepository<UserStats,Long> {



    List<UserStats> findAllByOrderByDateOfSignupDesc();
    //private long id;
    //    private long product_id;
    //    private long customer_id;
    //    private long company_id;
    //    private double price;
    //    private LocalDateTime dateOfPurchase;
//    Optional<List<PurchaseStats>> findAllByCustomerId(long customerId);
//    Optional<List<PurchaseStats>> findAllByCompanyId(long companyId);


}
