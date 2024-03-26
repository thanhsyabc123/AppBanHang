package com.example.doanmobile.chainofres;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthenticationHandler extends BaseLoginHandler {
    private Context context;

    public FirebaseAuthenticationHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleLogin(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Authentication successful, proceed to the next screen
                    } else {
                        // Authentication failed, handle the error
                        Toast.makeText(context, "Đăng nhập thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
