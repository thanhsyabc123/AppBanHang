package com.example.doanmobile.chainofres;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doanmobile.R;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        // Initialize UI components
        emailEditText = findViewById(R.id.Emaildangnhap);
        passwordEditText = findViewById(R.id.Matkhaudangnhap);

        // Configure the chain of responsibility
        BaseLoginHandler emailValidationHandler = new EmailValidationHandler(this);
        BaseLoginHandler passwordValidationHandler = new PasswordValidationHandler(this);
        BaseLoginHandler firebaseAuthenticationHandler = new FirebaseAuthenticationHandler(this);

        emailValidationHandler.setNextHandler(passwordValidationHandler);
        passwordValidationHandler.setNextHandler(firebaseAuthenticationHandler);

        // Handle login button click
        findViewById(R.id.btnDangNhap).setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Start the chain of responsibility
            emailValidationHandler.handleLogin(email, password);
        });
    }
}