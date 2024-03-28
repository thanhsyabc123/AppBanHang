package com.example.doanmobile.factory_method;

import android.content.Context;
import android.content.Intent;

import com.example.doanmobile.Order.PaymentSuccessActivity;

public class CashPayment implements PaymentMethod {
    private Context mContext;

    public CashPayment(Context context) {
        this.mContext = context;
    }

    @Override
    public void pay(String orderId, String  detailId, double totalAmount) {
        // Thực hiện thanh toán bằng tiền mặt
        Intent intent = new Intent(mContext, PaymentSuccessActivity.class);
        intent.putExtra("orderId", orderId);
        intent.putExtra("detailId", detailId);
        mContext.startActivity(intent);
    }
}
