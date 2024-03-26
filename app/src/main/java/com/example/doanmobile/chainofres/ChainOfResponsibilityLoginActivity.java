package com.example.doanmobile.chainofres;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doanmobile.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ChainOfResponsibilityLoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private AuthenticationHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        emailEditText = findViewById(R.id.Emaildangnhap);
        passwordEditText = findViewById(R.id.Matkhaudangnhap);

        handler = new EmailPasswordAuthHandler(this, mAuth, firestore,
                findViewById(R.id.btnDangNhap),
                findViewById(R.id.chuyensangdangkytaikhoan),
                findViewById(R.id.quenmatkhau));

        // Set next handler if needed
        // handler.setNext(new GoogleSignInHandler(this, mAuth, firestore,
        //         findViewById(R.id.dangnhapgmail)));

        // Set final handler
        // handler.setFinalHandler(new GuestHandler(this));
    }

    public void authenticate(View view) {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        handler.handleAuthentication(email, password);
    }
}
