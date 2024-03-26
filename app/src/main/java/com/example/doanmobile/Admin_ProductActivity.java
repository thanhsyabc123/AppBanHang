package com.example.doanmobile;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanmobile.dangsanpham.ProductAdapter;
import com.example.doanmobile.dangsanpham.Products;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Admin_ProductActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Products> productsList;
    ProductAdapter productAdapter;
    CollectionReference productCollection;
    private List<Integer> favoriteProducts = new ArrayList<>();
    private int shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product);

        // Lấy shopId từ Intent
        shopId = getIntent().getIntExtra("shopId", 0);

        recyclerView = findViewById(R.id.rv_productList);
        productsList = new ArrayList<>();
        productAdapter = new ProductAdapter(Admin_ProductActivity.this, productsList);
        productAdapter.updateProductFavoriteStatus(favoriteProducts);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(Admin_ProductActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(productAdapter);

        productCollection = FirebaseFirestore.getInstance().collection("Shop");
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_ProductActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        loadFavoriteProducts();
        productCollection.whereEqualTo("shopID", shopId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            // Xử lý lỗi'
                            Toast.makeText(Admin_ProductActivity.this, shopId, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(Admin_ProductActivity.this, shopId, Toast.LENGTH_SHORT).show();
                        productsList.clear();
                        for (DocumentSnapshot documentSnapshot : value) {
                            Products products = documentSnapshot.toObject(Products.class);
                            if (products != null) {
                                productsList.add(products);
                            }
                        }
                        productAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
    }

    private void loadFavoriteProducts() {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        if (user != null) {
            String userId = user.getUid();
            fStore.collection("KhachHang").document(userId).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            int userID = documentSnapshot.getLong("userID").intValue();
                            fStore.collection("favorites")
                                    .whereEqualTo("userID", userID)
                                    .get()
                                    .addOnSuccessListener(queryDocumentSnapshots -> {
                                        favoriteProducts.clear();
                                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                                            int productID = document.getLong("productID").intValue();
                                            favoriteProducts.add(productID);
                                        }
                                        if (productAdapter != null) {
                                            productAdapter.updateProductFavoriteStatus(favoriteProducts);
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        // Xảy ra lỗi khi truy vấn Firestore
                                    });
                        }
                    });
        }
    }
}
