package com.example.doanmobile.FirebaseFacade;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FirebaseFacade {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private GoogleSignInClient mGoogleSignInClient;

    public FirebaseFacade(FirebaseAuth auth, FirebaseFirestore firestore, GoogleSignInClient googleSignInClient) {
        mAuth = auth;
        db = firestore;
        mGoogleSignInClient = googleSignInClient;
    }

    // Register user with email and password
    public void registerWithEmailPassword(String email, String password, OnRegistrationListener listener) {

        mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                SignInMethodQueryResult result = task.getResult();
                List<String> signInMethods = result.getSignInMethods();
                // check xem tai khoan nay dc dang nhap chua
                if (signInMethods != null && signInMethods.contains(EmailAuthProvider.EMAIL_PASSWORD_SIGN_IN_METHOD)) {
                    listener.onFailure("Email đã được sử dụng");
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    //dk thanh cong
                                    listener.onSuccess();
                                } else {
                                    //dk that bai hien ra loi
                                    String errorMessage = task1.getException() != null ? task1.getException().getMessage() : "Unknown error";
                                    listener.onFailure(errorMessage);
                                }
                            });
                }
            } else {
                listener.onFailure("Lỗi khi kiểm tra email");
            }
        });
    }
    public void signInWithEmailPassword(String email, String password, OnSignInListener listener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onSuccess();
                    } else {
                        listener.onFailure("Đăng nhập thất bại.");
                    }
                });
    }

    // Listener interface for sign-in events
    public interface OnSignInListener {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    // Register user with Google
    public void registerWithGoogle(String idToken, OnRegistrationListener listener) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onSuccess();
                    } else {
                        // Registration with Google failed
                        listener.onFailure("Đăng nhập thất bại.");
                    }
                });
    }

    // Listener interface for registration events
    public interface OnRegistrationListener {
        void onSuccess();
        void onFailure(String errorMessage);
    }
    public void resetPassword(String email, OnResetPasswordListener listener) {
        mAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        listener.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onFailure(e.getMessage());
                    }
                });
    }

    // Listener interface for reset password events
    public interface OnResetPasswordListener {
        void onSuccess();
        void onFailure(String errorMessage);
    }
}
