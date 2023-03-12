package com.coupons.couponsystem.dto.response;

import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.model.Customer;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter

public class PurchaseDTO {
    private long id;
    private LocalDateTime purchaseDate;

    private double totalPrice;

    private long customerId;

    private boolean paymentApproval;

    private List<Coupon> coupons;

    public PurchaseDTO( long id, LocalDateTime purchaseDate, double totalPrice, Customer customer, boolean paymentApproval, List<Coupon> coupons) {
//        super(message);
        this.id = id;
        this.purchaseDate = purchaseDate;
        this.totalPrice = totalPrice;
        this.customerId = customer.getId();
        this.paymentApproval = paymentApproval;
        this.coupons = coupons;
    }
}
