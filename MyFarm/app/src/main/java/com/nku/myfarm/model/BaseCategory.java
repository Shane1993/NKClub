package com.nku.myfarm.model;

/**
 * Created by Shane on 2017/3/30.
 */

public abstract class BaseCategory {

    private int id;
    private String name;
    private String img;

    public BaseCategory(){}

    public BaseCategory(int id, String name, String img) {
        this.id = id;
        this.name = name;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }


    @Override
    public String toString() {
        return
                "id=" + id +
                ", name='" + name + '\'' +
                ", img='" + img + '\'';
    }
}
