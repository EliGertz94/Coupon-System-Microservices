package com.coupons.couponsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "purchase")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private LocalDateTime purchaseDate;

    private double totalPrice;

//    @JoinTable(name = "customer_coupons",
//            @JoinColumn(name = "company_id", referencedColumnName = "id")

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonIgnore
    private Customer customer;

    private boolean paymentApproval;
//    @OneToMany(fetch = FetchType.EAGER,
//            cascade = CascadeType.DETACH)
//    private List<Coupon> coupons;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.DETACH)
    @JoinTable(name = "purchases_coupons",
            joinColumns = @JoinColumn(name = "purchases_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id" ,referencedColumnName = "id"))
    @ToString.Exclude
    private List<Coupon> coupons;


}
