package com.example.doanmobile.chainofres;

public class PasswordValidationHandler extends BaseLoginHandler{
    @Override
    public void handleLogin(String email, String password) {
        if (isValidPassword(password)) {
            if (nextHandler != null) {
                nextHandler.handleLogin(email, password);
            }
        } else {
            // Handle invalid password
        }
    }

    private boolean isValidPassword(String password) {
        // Perform password validation logic
        return password != null && password.length() >= 6; // Example validation: Minimum length of 6 characters
    }
}
