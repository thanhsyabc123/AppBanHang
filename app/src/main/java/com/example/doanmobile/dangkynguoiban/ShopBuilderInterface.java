package com.example.doanmobile.dangkynguoiban;

public interface ShopBuilderInterface {
    ShopBuilderInterface shopId(int shopId);
    ShopBuilderInterface userId(int userId);
    ShopBuilderInterface shopName(String shopName);
    ShopBuilderInterface diaChi(String diaChi);
    ShopBuilderInterface moTa(String moTa);
    Shop build();
}