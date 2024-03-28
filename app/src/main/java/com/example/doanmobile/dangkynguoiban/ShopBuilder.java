// File: ShopBuilder.java
package com.example.doanmobile.dangkynguoiban;

public class ShopBuilder {

    private int shopId;
    private int userId;
    private String shopName;
    private String diaChi;
    private String moTa;

    public ShopBuilder() {
    }

    public ShopBuilder shopId(int shopId) {
        this.shopId = shopId;
        return this;
    }

    public ShopBuilder userId(int userId) {
        this.userId = userId;
        return this;
    }

    public ShopBuilder shopName(String shopName) {
        this.shopName = shopName;
        return this;
    }

    public ShopBuilder diaChi(String diaChi) {
        this.diaChi = diaChi;
        return this;
    }

    public ShopBuilder moTa(String moTa) {
        this.moTa = moTa;
        return this;
    }

    public Shop build() {
        return new Shop(shopId, userId, shopName, diaChi, moTa);
    }
}
