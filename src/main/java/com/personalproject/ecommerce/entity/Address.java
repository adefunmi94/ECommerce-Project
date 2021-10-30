package com.personalproject.ecommerce.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Address {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private String city;

    private String country;

    private String state;
    private String zipCode;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Order order;

}
