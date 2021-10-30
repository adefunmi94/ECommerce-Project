package com.personalproject.ecommerce.dto;


import com.personalproject.ecommerce.entity.Address;
import com.personalproject.ecommerce.entity.Customer;
import com.personalproject.ecommerce.entity.Order;
import com.personalproject.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class Purchase {

    private Customer customer;

    private Address shippingAddress;

    private Address billingAddress;

    private Order order;

    private Set<OrderItem> orderItems;

    public String generateOrderTrackingNumber() {

//        generate random VUID number (VUID )

        return UUID.randomUUID().toString();
    }
}
