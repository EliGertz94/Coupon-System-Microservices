package com.coupons.couponsystem.model;

import com.coupons.couponsystem.clientLogIn.ClientType;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;
    @Column(name = "client_Role")
    @Enumerated(EnumType.STRING)
    private ClientType clientRole;



}
