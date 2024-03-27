package com.example.doanmobile.factory_method;

import android.app.Activity;
import android.content.Context;

public class MomoPaymentFactory implements PaymentMethodFactory {
    private Context mContext;

    public MomoPaymentFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public PaymentMethod createPaymentMethod() {
        return new MomoPayment((Activity) mContext);
    }
}
