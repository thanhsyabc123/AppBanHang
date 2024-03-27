package com.example.doanmobile.chainofres;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doanmobile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        // Initialize UI components
        emailEditText = findViewById(R.id.Emaildangnhap);
        passwordEditText = findViewById(R.id.Matkhaudangnhap);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Configure the chain of responsibility
        BaseLoginHandler emailValidationHandler = new EmailValidationHandler(this);
        BaseLoginHandler passwordValidationHandler = new PasswordValidationHandler(this);
        BaseLoginHandler firebaseAuthenticationHandler = new FirebaseAuthenticationHandler(this, mAuth, firestore);

        emailValidationHandler.setNextHandler(passwordValidationHandler);
        passwordValidationHandler.setNextHandler(firebaseAuthenticationHandler);

        // Handle login button click
        findViewById(R.id.btnDangNhap).setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                // Hiển thị thông báo lỗi nếu dữ liệu đầu vào không hợp lệ
                Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Start the chain of responsibility
            emailValidationHandler.handleLogin(email, password);
        });
    }
}