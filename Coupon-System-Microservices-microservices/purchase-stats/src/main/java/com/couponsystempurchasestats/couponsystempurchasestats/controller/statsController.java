package com.couponsystempurchasestats.couponsystempurchasestats.controller;

import com.couponsystempurchasestats.couponsystempurchasestats.DTO.Request;
import com.couponsystempurchasestats.couponsystempurchasestats.entity.PurchaseStats;
import com.couponsystempurchasestats.couponsystempurchasestats.service.PurchaseStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("stats/")
public class statsController {

    @Autowired
    private PurchaseStatsService service;

    @PostMapping
    public ResponseEntity<String> addRecord(@RequestBody Request request) {
        service.addRecord(request);

        return new ResponseEntity<>("record added", HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<PurchaseStats>> findAll() {
        try {
            return new ResponseEntity<>(service.getAllPurchases(), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("customer/{customerId}")
    public ResponseEntity<List<PurchaseStats>> findCustomerSales(@PathVariable long customerId) {
        try {
            return new ResponseEntity<>(service.customerPurchases(customerId), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("company/{companyId}")
    public ResponseEntity<List<PurchaseStats>> findCompanySingleSales(@PathVariable long companyId) {
        try {
            return new ResponseEntity<>(service.companyPurchases(companyId), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





}
