package com.example.doanmobile.factory_method;

import android.content.Context;

public class CashPaymentFactory implements PaymentMethodFactory {
    private Context mContext;

    public CashPaymentFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public PaymentMethod createPaymentMethod() {
        return new CashPayment(mContext);
    }
}