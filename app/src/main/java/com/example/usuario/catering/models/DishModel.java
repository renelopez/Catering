package com.example.usuario.catering.models;

import java.io.Serializable;

/**
 * Created by Usuario on 28/11/2014.
 */
public class DishModel implements Serializable{
    public int id;
    public String name;
    private String formattedDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
