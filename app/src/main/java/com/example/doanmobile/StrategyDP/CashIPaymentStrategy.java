package com.example.doanmobile.StrategyDP;

import android.content.Context;
import android.content.Intent;

import com.example.doanmobile.hoadon.thanhtoanthanhcong;

public class CashIPaymentStrategy implements iPaymentStrategy {
    private Context context;
    private int neworderId;
    private int currentDetailID;

    public CashIPaymentStrategy(Context context, int neworderId, int currentDetailID) {
        this.context = context;
        this.neworderId = neworderId;
        this.currentDetailID = currentDetailID;
    }

    @Override
    public void pay(double amount) {
        // Logic for cash payment
        Intent intent = new Intent(context, thanhtoanthanhcong.class);
        intent.putExtra("orderId", neworderId);
        intent.putExtra("detailId", currentDetailID);
        context.startActivity(intent);
    }
}
