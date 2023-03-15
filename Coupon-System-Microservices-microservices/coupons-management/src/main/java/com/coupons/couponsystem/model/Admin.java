package com.coupons.couponsystem.model;

import com.coupons.couponsystem.clientLogIn.ClientType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "admin")
@EqualsAndHashCode(of = "id")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;
    @Column(name = "client_Role")
    @Enumerated(EnumType.STRING)
    private  ClientType clientRole;



}
