package com.example.doanmobile.chainofres;

import android.content.Context;
import android.widget.Toast;

import com.example.doanmobile.dangky;

public class PasswordValidationHandler extends BaseLoginHandler{
    private Context context;
    public PasswordValidationHandler(Context context) {
        this.context = context;
    }
    @Override
    public void handleLogin(String email, String password) {
        if (isValidPassword(password)) {
            if (nextHandler != null) {
                nextHandler.handleLogin(email, password);
            }
        } else {
            Toast.makeText(context, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidPassword(String password) {
        // Perform password validation logic
        return password != null && password.length() >= 6; // Example validation: Minimum length of 6 characters
    }
}
