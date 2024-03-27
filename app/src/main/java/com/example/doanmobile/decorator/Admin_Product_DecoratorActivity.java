package com.example.doanmobile.decorator;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanmobile.R;
import com.example.doanmobile.dangsanpham.ProductAdapter;
import com.example.doanmobile.dangsanpham.Products;
import com.example.doanmobile.dangsanpham.UploadCategory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Admin_Product_DecoratorActivity extends AppCompatActivity implements IProducts{
    RecyclerView recyclerView;
    List<Products> productsList;
    ProductAdapter productAdapter;
    CollectionReference productCollection;
    private List<Integer> favoriteProducts = new ArrayList<>();
    Button addProduct;
    private int shopId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product);

        // Lấy shopId từ Intent
        shopId = getIntent().getIntExtra("shopId", 1);

        recyclerView = findViewById(R.id.rv_productList);
        productsList = new ArrayList<>();
        productAdapter = new ProductAdapter(Admin_Product_DecoratorActivity.this, productsList);
        productAdapter.updateProductFavoriteStatus(favoriteProducts);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(Admin_Product_DecoratorActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(productAdapter);


        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Product_DecoratorActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        loadFavoriteProducts();
        productCollection = FirebaseFirestore.getInstance().collection("Products");
        productCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    // Xử lý lỗi
                    return;
                }
                productsList.clear();
                for (DocumentSnapshot documentSnapshot : value) {
                    Products products = documentSnapshot.toObject(Products.class);
                    if (products.getShopID() == shopId) {
                        productsList.add(products);
                    }
                }
                productAdapter.notifyDataSetChanged();
                dialog.dismiss();

            }
        });
        addProduct = findViewById(R.id.add_product);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Product_DecoratorActivity.this, UploadCategory.class);
                startActivity(intent);
            }
        });
    }

    private void loadFavoriteProducts() {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        String userId = user.getUid();
        DocumentReference userRef = fStore.collection("KhachHang").document(userId);
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    int userID = documentSnapshot.getLong("userID").intValue();

                    // Tiếp tục xử lý với userID tại đây...

                    // Lấy reference đến collection "favorites" và thực hiện truy vấn
                    fStore.collection("favorites")
                            .whereEqualTo("userID", userID)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    favoriteProducts.clear();
                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                        // Lấy productID từ document
                                        int productID = document.getLong("productID").intValue();
                                        favoriteProducts.add(productID);
                                    }

                                    // Cập nhật vào adapter
                                    if (productAdapter != null) {
                                        productAdapter.updateProductFavoriteStatus(favoriteProducts);
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@androidx.annotation.NonNull Exception e) {
                                    // Xảy ra lỗi khi truy vấn Firestore
                                }
                            });
                }
            }
        });
    }
}