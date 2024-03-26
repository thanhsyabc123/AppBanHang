package com.example.doanmobile.chainofres;

public interface AuthenticationHandler {
    void setNext(AuthenticationHandler handler);
    void setFinalHandler(AuthenticationHandler handler);
    void handleAuthentication(String email, String password);
}
