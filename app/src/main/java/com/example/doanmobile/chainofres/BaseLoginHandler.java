package com.example.doanmobile.chainofres;

public abstract class BaseLoginHandler implements LoginHandler{
    protected LoginHandler nextHandler;

    public void setNextHandler(LoginHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
