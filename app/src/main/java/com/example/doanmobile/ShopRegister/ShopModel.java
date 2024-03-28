package com.example.doanmobile.ShopRegister;

public class ShopModel {
    private int shopId;
    private int userId;
    private String shopName;
    private String diaChi;
    private String moTa;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public ShopModel() {}

    public ShopModel(int shopId, int userId, String shopName, String diaChi, String moTa) {
        this.shopId = shopId;
        this.userId = userId;
        this.shopName = shopName;
        this.diaChi = diaChi;
        this.moTa = moTa;

    }
}
