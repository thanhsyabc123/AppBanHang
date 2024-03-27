package com.example.doanmobile.factory_method;

public interface PaymentMethod {
    void pay(String orderId, String detailId, double totalAmount);
}

