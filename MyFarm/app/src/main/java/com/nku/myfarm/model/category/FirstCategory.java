package com.nku.myfarm.model.category;

import com.nku.myfarm.model.BaseCategory;

import java.util.Arrays;

/**
 * Created by Shane on 2017/3/27.
 */

public class FirstCategory extends BaseCategory{

    private SecondCategory[] cids;

    public FirstCategory() {
        super();
    }

    public FirstCategory(int id, String name, String img, SecondCategory[] cids) {
        super(id, name, img);
        this.cids = cids;
    }


    public SecondCategory[] getCids() {
        return cids;
    }

    public void setCids(SecondCategory[] cids) {
        this.cids = cids;
    }

    @Override
    public String toString() {
        return "FirstCategoty{" + super.toString() +
                ", cids=" + Arrays.toString(cids) +
                '}';
    }
}
