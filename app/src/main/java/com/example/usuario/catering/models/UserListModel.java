package com.example.usuario.catering.models;

import java.util.ArrayList;

/**
 * Created by rene.lopez on 12/10/2014.
 */
public class UserListModel {
    public ArrayList<UserModel> userList;

    public ArrayList<UserModel> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<UserModel> userList) {
        this.userList = userList;
    }
}
