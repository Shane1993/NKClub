package com.nku.myfarm.model;

/**
 * Created by Shane on 2017/6/6.
 */

public class ShopcartItem {

    private int id;
    private String name;
    private float price;
    private String img;

    private int count = 1;

    private boolean isSelected;

    public ShopcartItem(){}

    public ShopcartItem(int id, String name, float price, String img, int count, boolean isSelected) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.img = img;
        this.count = count;
        this.isSelected = isSelected;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "ShopcartItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", img='" + img + '\'' +
                ", count=" + count +
                ", isSelected=" + isSelected +
                '}';
    }
}
