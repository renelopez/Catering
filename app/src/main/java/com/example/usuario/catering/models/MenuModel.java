package com.example.usuario.catering.models;

import java.util.ArrayList;

/**
 * Created by rene.lopez on 12/15/2014.
 */
public class MenuModel {
    private int id;
    private String formattedDate;
    private ArrayList<DishModel> dishes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String dateTime) {
        this.formattedDate = formattedDate;
    }

    public ArrayList<DishModel> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<DishModel> dishes) {
        this.dishes = dishes;
    }
}
