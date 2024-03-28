package com.example.doanmobile.chainofres;

public abstract class BaseLoginHandler implements LoginHandler{
    protected LoginHandler nextHandler;
    public abstract void handleLogin(String email, String password);

    public void setNextHandler(LoginHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
