package com.personalproject.ecommerce.service;

import com.personalproject.ecommerce.dao.CustomerRepository;
import com.personalproject.ecommerce.dto.Purchase;
import com.personalproject.ecommerce.dto.PurchaseResponse;
import com.personalproject.ecommerce.entity.Customer;
import com.personalproject.ecommerce.entity.Order;
import com.personalproject.ecommerce.entity.OrderItem;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;


@Service
class CheckoutServiceImpl implements CheckoutService {

   private CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

//        retrieve the order info from dto
        Order order = purchase.getOrder();
//        generate tracking number

      String orderTrackingNumber =  purchase.generateOrderTrackingNumber();
      order.setOrderTrackingNumber(orderTrackingNumber);

      //        ppopulate order with orderItems

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

//        populate order with shippingAddress and billingAddres

        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress((purchase.getShippingAddress()));
//        populate customer with order
        Customer customer = purchase.getCustomer();
        customer.add(order);

//        save to the database
        customerRepository.save(customer);

//        return response

        return new PurchaseResponse(orderTrackingNumber);

//
    }
}
