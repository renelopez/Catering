package com.example.usuario.catering.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Usuario on 28/11/2014.
 */
public class DishModel implements Serializable{
    public int id;
    public String name;
    private Date datetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
