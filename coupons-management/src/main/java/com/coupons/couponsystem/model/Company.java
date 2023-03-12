package com.coupons.couponsystem.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;


    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Coupon> coupons ;

    @Builder
    public Company(Long id, String name,  List<Coupon> coupons) {
        this.id = id;
        this.name = name;
        if(coupons!=null){
            for (Coupon coupon :
                    coupons) {
                coupon.setCompany(this);
            }

            this.coupons=coupons;
        }else {
            this.coupons = new LinkedList<>();
        }
    }

    public void setCoupons(List<Coupon> coupons) {
        if(coupons!=null){
            for (Coupon coupon :
                    coupons) {
                coupon.setCompany(this);
            }
        }else{
            coupons= new LinkedList<>();
        }

        this.coupons=coupons;
    }



}
