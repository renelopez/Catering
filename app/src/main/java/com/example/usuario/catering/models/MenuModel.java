package com.example.usuario.catering.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rene.lopez on 12/15/2014.
 */
public class MenuModel {
    private int id;
    private Date dateTime;
    private ArrayList<DishModel> dishes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public ArrayList<DishModel> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<DishModel> dishes) {
        this.dishes = dishes;
    }
}
