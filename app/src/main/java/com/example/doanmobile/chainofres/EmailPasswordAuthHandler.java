package com.example.doanmobile.chainofres;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class EmailPasswordAuthHandler implements AuthenticationHandler{
    private AuthenticationHandler nextHandler;
    private AuthenticationHandler finalHandler;

    private AppCompatActivity activity;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private View signInButton;
    private View signUpTextView;
    private View forgotPasswordTextView;

    public EmailPasswordAuthHandler(AppCompatActivity activity, FirebaseAuth mAuth, FirebaseFirestore firestore,
                                    View signInButton, View signUpTextView, View forgotPasswordTextView) {
        this.activity = activity;
        this.mAuth = mAuth;
        this.firestore = firestore;
        this.signInButton = signInButton;
        this.signUpTextView = signUpTextView;
        this.forgotPasswordTextView = forgotPasswordTextView;
    }

    @Override
    public void setNext(AuthenticationHandler handler) {
        nextHandler = handler;
    }

    @Override
    public void setFinalHandler(AuthenticationHandler handler) {
        finalHandler = handler;
    }

    @Override
    public void handleAuthentication(String email, String password) {
        // Perform email/password authentication
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        // Handle success
                    } else {
                        // Pass to the next handler
                        if (nextHandler != null) {
                            nextHandler.handleAuthentication(email, password);
                        }
                    }
                });
    }
}
