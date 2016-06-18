package net.lzs.club.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.R.attr.type;
import static android.R.attr.value;

/**
 * Created by LEE on 2016/4/14.
 */
public class Config
{
    public static final String APP_NAME = "NKUClub";

    public static final String TOKEN = "token";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String CLUB = "club";
    public static final String ACTIVITY = "activity";

    public static final String OBJECTID = "objectId";

    public static final String ACTIVITYNAME = "activityName";
    public static final String ACTIVITYORGANIZER = "activityOrganizer";
    public static final String ACTIVITYTIME = "activityTime";
    public static final String ACTIVITYCONTENT = "activityContent";

    public static final String CLUBNAME = "clubName";
    public static final String CLUBTYPE = "clubType";
    public static final String CLUBTIME = "clubTime";
    public static final String CLUBDESCRIPTION = "clubDescription";
    public static final String CLUBBELONGS = "clubBelongs";
    public static final String CLUBICONURI = "clubIconUri";


    public static void cacheData(Context context,String data,CacheType type)
    {
        SharedPreferences.Editor e = context.getApplicationContext().getSharedPreferences(APP_NAME,Context.MODE_PRIVATE).edit();
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
