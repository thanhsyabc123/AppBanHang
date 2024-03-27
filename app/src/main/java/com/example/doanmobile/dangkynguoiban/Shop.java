package com.example.doanmobile.dangkynguoiban;

public class Shop {

    private int shopId;
    private int userId;
    private String shopName;
    private String diaChi;
    private String moTa;

    // ẩn đi constructor của Shop
    private Shop(Builder builder) {
        this.shopId = builder.shopId;
        this.userId = builder.userId;
        this.shopName = builder.shopName;
        this.diaChi = builder.diaChi;
        this.moTa = builder.moTa;
    }

    public int getShopId() {
        return shopId;
    }

    public int getUserId() {
        return userId;
    }

    public String getShopName() {
        return shopName;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getMoTa() {
        return moTa;
    }
    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    // Design Pattern Builder

    public static class Builder {
        private int shopId;
        private int userId;
        private String shopName;
        private String diaChi;
        private String moTa;

        public Builder() {

        }

        public Builder shopId(int shopId) {
            this.shopId = shopId;
            return this;
        }

        public Builder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder shopName(String shopName) {
            this.shopName = shopName;
            return this;
        }

        public Builder diaChi(String diaChi) {
            this.diaChi = diaChi;
            return this;
        }

        public Builder moTa(String moTa) {
            this.moTa = moTa;
            return this;
        }

        public Shop build() {
            return new Shop(this);
        }
    }
}
