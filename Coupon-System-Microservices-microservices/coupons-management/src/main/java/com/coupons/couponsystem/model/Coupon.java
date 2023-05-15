package com.coupons.couponsystem.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "coupon")
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    @ToString.Exclude
    @JsonIgnore
    private Company company;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imageId")
    private ImageData imageData;
    @Enumerated(EnumType.ORDINAL)
    @Column(name="category")
    private Category category;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private int originalAmount;
    private int amount;
    private double price;
    private boolean buyable;

    private String image;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.DETACH)
    @JoinTable(name = "purchases_coupons",
            joinColumns = @JoinColumn(name = "coupon_id" ,referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "purchases_id", referencedColumnName = "id"))
    @ToString.Exclude
    @JsonIgnore
    private List<Purchase> purchases;


    //get rid of the cascaden
//    @ManyToMany(fetch = FetchType.EAGER,
//            cascade = CascadeType.DETACH)
//    @JoinTable(name = "customer_coupons",
//            joinColumns = @JoinColumn(name = "coupon_id" ,referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"))
//    @ToString.Exclude
//    @JsonIgnore
//    private List<Customer> customers;



}
