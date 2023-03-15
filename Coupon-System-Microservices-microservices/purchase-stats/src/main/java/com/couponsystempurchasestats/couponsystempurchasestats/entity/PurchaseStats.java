package com.couponsystempurchasestats.couponsystempurchasestats.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "purchase_stats")
public class PurchaseStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long productId;
    private long customerId;
    private long companyId;
    private double price;
    private LocalDateTime dateOfPurchase;


}
