package com.personalproject.ecommerce.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private  String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<Order> orders = new HashSet<>();

    public void add(Order order){
        if(order != null){

            if (orders == null){
                orders = new HashSet<>();
            }

            orders.add(order);
            order.setCustomer(this);
        }
    }
}
