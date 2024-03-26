package com.example.doanmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanmobile.FirebaseFacade.FirebaseFacade;
import com.example.doanmobile.dangkynguoiban.manhinhnguoiban;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class dangky extends AppCompatActivity {
    EditText Tendaydu, Sodienthoai, Email, Matkhau;
    ImageButton btnDangKiTaiKhoan;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    TextView chuyensangdangnhap;
    CheckBox dangkynguoidung;
    ImageButton loginwithgoogle;
    private GoogleSignInClient mGoogleSignInClient;
    private  static final int RC_SIGN_IN = 1;
    private static final String TAG = "MyActivity";
    private FirebaseFacade firebaseFacade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        KhachHang khachHang = new KhachHang();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseFacade = new FirebaseFacade(mAuth, db, mGoogleSignInClient);
        loginwithgoogle = findViewById(R.id.loginwithgoogle);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        dangkynguoidung = findViewById(R.id.dangkynguoidung);
        dangkynguoidung.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              if(compoundButton.isChecked()) {
                  khachHang.setNguoiBan(false);
                  khachHang.setNguoiBanVip(false);
              }
            }
        });
        Tendaydu = findViewById(R.id.Tendaydu);
        Sodienthoai = findViewById(R.id.Sodienthoai);
        Email = findViewById(R.id.Email);
        Matkhau = findViewById(R.id.Matkhau);
        btnDangKiTaiKhoan = findViewById(R.id.BtnDangKiTaiKhoan);
        chuyensangdangnhap = findViewById(R.id.chuyensangdangnhap);
        chuyensangdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(dangky.this, dangnhap.class);
                startActivity(intent);
            }
        });
        //dangnhap bang gg
        loginwithgoogle.setOnClickListener(view -> {
            // Get Google sign-in intent
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
        btnDangKiTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenDayDu = Tendaydu.getText().toString().trim();
                String soDienThoai = Sodienthoai.getText().toString().trim();
                String email = Email.getText().toString().trim();
                String matKhau = Matkhau.getText().toString().trim();
                if (!(dangkynguoidung.isChecked())){
                    Toast.makeText(dangky.this,"Vui lòng nhấn đồng ý",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tenDayDu.isEmpty() || soDienThoai.isEmpty() || email.isEmpty() || matKhau.isEmpty()) {
                    Toast.makeText(dangky.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
               //Facade
                firebaseFacade.registerWithEmailPassword(email, matKhau, new FirebaseFacade.OnRegistrationListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(dangky.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        if (khachHang.isKhachHang()) {
                            Intent intent = new Intent(dangky.this,trangchunguoidung.class);
                            startActivity(intent);
                        } else if (khachHang.isNguoiBan()) {
                            Intent intent = new Intent(dangky.this, manhinhnguoiban.class);
                            startActivity(intent);
                        } else if (khachHang.isNguoiBanVip()) {
                            Intent intent = new Intent(dangky.this, manhinhnguoiban.class); // Chuyển hướng đến giao diện B hoặc giao diện khác cho người bán VIP
                            startActivity(intent);
                        }
                        finish();
                    }
                    @Override
                    public void onFailure(String errorMessage) {
                        // Registration failed
                        Toast.makeText(dangky.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String idToken = account.getIdToken();
                firebaseFacade.registerWithGoogle(idToken, new FirebaseFacade.OnRegistrationListener() {
                    @Override
                    public void onSuccess() {
                        // Registration with Google successful
                        Toast.makeText(dangky.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent = new Intent(dangky.this, dangkygmail.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onFailure(String errorMessage) {
                        // Registration with Google failed
                        Toast.makeText(dangky.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (ApiException e) {
                Log.w(TAG, "Google sign-in failed", e);
                Toast.makeText(dangky.this, "Đăng nhập thất bại.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
