package com.couponsystempurchasestats.couponsystempurchasestats.service;

import com.couponsystempurchasestats.couponsystempurchasestats.DTO.Request;
import com.couponsystempurchasestats.couponsystempurchasestats.entity.PurchaseStats;
import com.couponsystempurchasestats.couponsystempurchasestats.repository.PurchaseStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PurchaseStatsService {

    @Autowired
    private PurchaseStatsRepository repository;

    public void addRecord(Request request) {
       PurchaseStats purchaseStats= PurchaseStats
               .builder()
               .id(0)
               .productId(request.getProduct_id())
               .customerId(request.getCustomer_id())
               .companyId(request.getCompany_id())
               .price(request.getPrice())
               .dateOfPurchase( LocalDateTime.now())
               .build();

        repository.save(purchaseStats);
    }

    public List<PurchaseStats> customerPurchases(long customerId) throws Exception {

       return repository.findAllByCustomerIdOrderByIdDesc(customerId).orElseThrow(()
                                -> new Exception( " no customer purchases found by the id   "));

    }

    public List<PurchaseStats> companyPurchases(long companyId) throws Exception {

        return repository.findAllByCompanyIdOrderByIdDesc(companyId).orElseThrow(()
                -> new Exception( " no company purchases found by the id   "));

    }


    public List<PurchaseStats> getAllPurchases() throws Exception {

        return repository.findTop5AllByOrderByIdDesc();

    }

}
