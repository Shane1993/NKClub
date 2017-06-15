package com.nku.myfarm.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Shane on 2017/4/21.
 */

public class Config {
    public static final String APP_NAME = "MyFarm";



    public static void cacheData(Context context,String data,CacheType type)
    {
        SharedPreferences.Editor e = context.getApplicationContext().getSharedPreferences(APP_NAME, Context.MODE_PRIVATE).edit();
        e.putString(type.name(),data);
        e.apply();
    }

    public static String getCachedData(Context context,CacheType type)
    {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(APP_NAME,Context.MODE_PRIVATE);
        return sp.getString(type.name(),"");

    }


    public static void cacheKVData(Context context,String key, String value)
    {
        SharedPreferences.Editor e = context.getApplicationContext().getSharedPreferences(APP_NAME,Context.MODE_PRIVATE).edit();
        e.putString(key,value);
        e.apply();
    }

    public static String getCachedKVData(Context context,String key)
    {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(APP_NAME,Context.MODE_PRIVATE);
        return sp.getString(key,"");

    }

}
