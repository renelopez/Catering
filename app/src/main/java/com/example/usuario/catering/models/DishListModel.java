package com.example.usuario.catering.models;

import java.util.ArrayList;

/**
 * Created by Usuario on 28/11/2014.
 */
public class DishListModel {
    public ArrayList<DishModel> dishList;

    public ArrayList<DishModel> getDishList() {
        return dishList;
    }

    public void setDishList(ArrayList<DishModel> dishList) {
        this.dishList = dishList;
    }
}
