package com.personalproject.ecommerce.entity;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "orders")
public class Order {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderTrackingNumber;

    private int totalQuantity;

    private String status;

    @CreationTimestamp
    private Date dateCreated;


    @UpdateTimestamp
    private Date lastUpdate;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
    private Address shippingAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id", referencedColumnName = "id")
    private Address billingAddress;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private Set<OrderItem> orderItems = new HashSet<>();

    public void add(OrderItem item){
        if(item != null){
            if(orderItems == null){
                orderItems = new HashSet<>();
            }
            orderItems.add(item);
            item.setOrder(this);
        }
    }
}
