package com.example.doanmobile.chainofres;

import android.text.TextUtils;

public class EmailValidationHandler extends BaseLoginHandler{
    @Override
    public void handleLogin(String email, String password) {
        if (isValidEmail(email)) {
            if (nextHandler != null) {
                nextHandler.handleLogin(email, password);
            }
        } else {
            // Handle invalid email
        }
    }

    private boolean isValidEmail(String email) {
        // Perform email validation logic
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
