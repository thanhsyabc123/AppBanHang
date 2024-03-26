package com.example.doanmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.doanmobile.FirebaseFacade.FirebaseFacade;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class quenmatkhau extends AppCompatActivity {
    ImageView backquenmatkhau;
    View btnSendResetLink;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    EditText Emailquenmatkhau;
    String email;
    private GoogleSignInClient mGoogleSignInClient;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quenmatkhau);


        Emailquenmatkhau = findViewById(R.id.Emailquenmatkhau);
        backquenmatkhau = findViewById(R.id.backquenmatkhau);
        backquenmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(quenmatkhau.this,dangnhap.class);
                startActivity(intent);
            }
        });
        btnSendResetLink = findViewById(R.id.btnSendResetLink);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        btnSendResetLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = Emailquenmatkhau.getText().toString().trim();
                if (!TextUtils.isEmpty(email)) {
                    resetPassword(email);
                } else {
                    Emailquenmatkhau.setError("Email không có trong tài khoản");
                }
            }
        });
    }
    private void resetPassword(String email) {
        FirebaseFacade firebaseFacade = new FirebaseFacade(mAuth, firestore, mGoogleSignInClient);
        firebaseFacade.resetPassword(email, new FirebaseFacade.OnResetPasswordListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(quenmatkhau.this, "Reset mật khẩu", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(quenmatkhau.this, dangnhap.class);
                startActivity(intent);
                finish();
            }
            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(quenmatkhau.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

}