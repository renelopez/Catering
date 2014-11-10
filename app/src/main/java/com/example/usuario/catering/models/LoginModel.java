package com.example.usuario.catering.models;

/**
 * Created by Usuario on 03/11/2014.
 */
public class LoginModel {
    private boolean isValid;
    private int userType;

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
