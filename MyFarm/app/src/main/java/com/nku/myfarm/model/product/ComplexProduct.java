package com.nku.myfarm.model.product;

import com.nku.myfarm.model.BaseProduct;

/**
 * Created by Shane on 2017/3/28.
 */

public abstract class ComplexProduct extends BaseProduct {

    private String attribute;
    private int sort;
    private int brandId;
    private String brandName;
    private int hotDegree;
    private int safeDay;
    private int safeUint;
    private int pcid;
    private int isMix;
    private int cartGroupId;
    private int sourceId;
    private String tagIds;
    private int preState;
    private String safeUnitDesc;
    private int cLayer;
    private String pCids;
    private String cids;
    private String productId;
    private String dealerId;
    private int number;
    private String dPrice;
    private int hadPm;
    private String pmDesc;
    private int isXf;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getHotDegree() {
        return hotDegree;
    }

    public void setHotDegree(int hotDegree) {
        this.hotDegree = hotDegree;
    }

    public int getSafeDay() {
        return safeDay;
    }

    public void setSafeDay(int safeDay) {
        this.safeDay = safeDay;
    }

    public int getSafeUint() {
        return safeUint;
    }

    public void setSafeUint(int safeUint) {
        this.safeUint = safeUint;
    }

    public int getPcid() {
        return pcid;
    }

    public void setPcid(int pcid) {
        this.pcid = pcid;
    }

    public int getIsMix() {
        return isMix;
    }

    public void setIsMix(int isMix) {
        this.isMix = isMix;
    }

    public int getCartGroupId() {
        return cartGroupId;
    }

    public void setCartGroupId(int cartGroupId) {
        this.cartGroupId = cartGroupId;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public int getPreState() {
        return preState;
    }

    public void setPreState(int preState) {
        this.preState = preState;
    }

    public String getSafeUnitDesc() {
        return safeUnitDesc;
    }

    public void setSafeUnitDesc(String safeUnitDesc) {
        this.safeUnitDesc = safeUnitDesc;
    }

    public int getcLayer() {
        return cLayer;
    }

    public void setcLayer(int cLayer) {
        this.cLayer = cLayer;
    }

    public String getpCids() {
        return pCids;
    }

    public void setpCids(String pCids) {
        this.pCids = pCids;
    }

    public String getCids() {
        return cids;
    }

    public void setCids(String cids) {
        this.cids = cids;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getdPrice() {
        return dPrice;
    }

    public void setdPrice(String dPrice) {
        this.dPrice = dPrice;
    }

    public int getHadPm() {
        return hadPm;
    }

    public void setHadPm(int hadPm) {
        this.hadPm = hadPm;
    }

    public String getPmDesc() {
        return pmDesc;
    }

    public void setPmDesc(String pmDesc) {
        this.pmDesc = pmDesc;
    }

    public int getIsXf() {
        return isXf;
    }

    public void setIsXf(int isXf) {
        this.isXf = isXf;
    }
}
