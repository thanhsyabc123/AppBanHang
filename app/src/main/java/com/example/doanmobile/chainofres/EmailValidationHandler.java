package com.example.doanmobile.chainofres;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class EmailValidationHandler extends BaseLoginHandler {
    private Context context;

    public EmailValidationHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleLogin(String email, String password) {
        if (isValidEmail(email)) {
            if (nextHandler != null) {
                nextHandler.handleLogin(email, password);
            }
        } else {
            Toast.makeText(context, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidEmail(String email) {
        // Perform email validation logic
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
