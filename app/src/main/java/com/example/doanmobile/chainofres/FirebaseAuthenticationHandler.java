package com.example.doanmobile.chainofres;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthenticationHandler extends BaseLoginHandler{
    @Override
    public void handleLogin(String email, String password) {
        // Perform Firebase authentication
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Authentication successful, proceed to the next screen
                    } else {
                        // Authentication failed, handle the error
                    }
                });
    }
}
