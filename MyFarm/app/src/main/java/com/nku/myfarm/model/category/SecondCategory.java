package com.nku.myfarm.model.category;

import com.nku.myfarm.model.BaseCategory;

/**
 * Created by Shane on 2017/3/27.
 */

public class SecondCategory extends BaseCategory{

    private int pcid;

    public SecondCategory() {
        super();
    }


    public SecondCategory(int id, String name, String img, int pcid) {
        super(id, name, img);
        this.pcid = pcid;
    }


    public int getPcid() {
        return pcid;
    }

    public void setPcid(int pcid) {
        this.pcid = pcid;
    }


    @Override
    public String toString() {
        return "SecondCategoty{" + super.toString() +
                ", pcid=" + pcid +
                '}';
    }
}
