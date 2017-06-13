package com.nku.myfarm.model;

/**
 * Created by Shane on 2017/3/27.
 */

public abstract class BaseProduct {

    private int id;
    private String name;
    private int storeNums;
    private String specifics;
    private float marketPrice;
    private float partnerPrice;
    private String preImg;
    private String preImgs;
    private int categoryId;
    private int cid;
    private int childCid;
    private float price;
    private String img;

    public BaseProduct() {}

    public BaseProduct(int id, String name, int storeNums, String specifics,
                       float marketPrice, float partnerPrice, String preImg,
                       String preImgs, int categoryId, int cid, int childCid,
                       float price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.storeNums = storeNums;
        this.specifics = specifics;
        this.marketPrice = marketPrice;
        this.partnerPrice = partnerPrice;
        this.preImg = preImg;
        this.preImgs = preImgs;
        this.categoryId = categoryId;
        this.cid = cid;
        this.childCid = childCid;
        this.price = price;
        this.img = imgUrl;
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

    public int getStoreNums() {
        return storeNums;
    }

    public void setStoreNums(int storeNums) {
        this.storeNums = storeNums;
    }

    public String getSpecifics() {
        return specifics;
    }

    public void setSpecifics(String specifics) {
        this.specifics = specifics;
    }

    public float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public float getPartnerPrice() {
        return partnerPrice;
    }

    public void setPartnerPrice(float partnerPrice) {
        this.partnerPrice = partnerPrice;
    }

    public String getPreImg() {
        return preImg;
    }

    public void setPreImg(String preImg) {
        this.preImg = preImg;
    }

    public String getPreImgs() {
        return preImgs;
    }

    public void setPreImgs(String preImgs) {
        this.preImgs = preImgs;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getChildCid() {
        return childCid;
    }

    public void setChildCid(int childCid) {
        this.childCid = childCid;
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

    public void setImg(String imgUrl) {
        this.img = imgUrl;
    }
}
