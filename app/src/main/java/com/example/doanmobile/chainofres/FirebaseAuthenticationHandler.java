package com.example.doanmobile.chainofres;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.doanmobile.KhachHang;
import com.example.doanmobile.dangkynguoiban.manhinhnguoiban;
import com.example.doanmobile.dangnhap;
import com.example.doanmobile.trangchunguoidung;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseAuthenticationHandler extends BaseLoginHandler {
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    public FirebaseAuthenticationHandler(Context context, FirebaseAuth mAuth, FirebaseFirestore firestore) {
        this.context = context;
        this.mAuth = mAuth;
        this.firestore = firestore;
    }

    @Override
    public void handleLogin(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Authentication successful, navigate to HomeActivity
                        FirebaseUser user = mAuth.getCurrentUser();
                        String uid = user.getUid();

                        DocumentReference df = firestore.collection("KhachHang").document(uid);


                        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    KhachHang khachHang = documentSnapshot.toObject(KhachHang.class);

                                    if (khachHang != null) {
                                        if (khachHang.isKhachHang()) {
                                            Intent intent = new Intent(context, trangchunguoidung.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);
                                        } else if (khachHang.isNguoiBan()) {
                                            Intent intent = new Intent(context, manhinhnguoiban.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);
                                        } else if (khachHang.isNguoiBanVip()) {
                                            Intent intent = new Intent(context, manhinhnguoiban.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);
                                        }
                                    }
                                } else {
                                    Toast.makeText(context, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
//                        Intent intent = new Intent(context, trangchunguoidung.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent);
                    } else {
                        // Authentication failed, handle the error
                        Toast.makeText(context, "Đăng nhập thất bại: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
