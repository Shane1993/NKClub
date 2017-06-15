package com.nku.myfarm.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Shane on 2017/6/7.
 */

public class MyToast {

    private final static boolean isShow = true;

    private MyToast(){
        throw new UnsupportedOperationException("T cannot be instantiated");
    }

    public static void showShort(Context context, CharSequence text) {
        if(isShow) Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context,CharSequence text) {
        if(isShow)Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }
}
