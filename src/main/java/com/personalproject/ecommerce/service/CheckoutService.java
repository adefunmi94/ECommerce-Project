package com.personalproject.ecommerce.service;

import com.personalproject.ecommerce.dto.Purchase;
import com.personalproject.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}
