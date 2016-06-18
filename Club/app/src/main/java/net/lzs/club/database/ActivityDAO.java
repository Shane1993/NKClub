package net.lzs.club.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.lzs.club.model.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LEE on 2016/4/15.
 */
public class ActivityDAO
{
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public ActivityDAO(Context context)
    {
        helper = DatabaseHelper.getInstance(context);
    }

    public void add(Activity activity)
    {
        db = helper.getWritableDatabase();
        db.execSQL("insert into activity(" +
                        "object_id," +
                        "name," +
                        "organizer," +
                        "time," +
                        "content) values (?,?,?,?,?)",
                new Object[]{
                        activity.getObjectId(),
                        activity.getName(),
                        activity.getOrganizer(),
                        activity.getTime(),
                        activity.getContent()
                });
    }

    public void update(Activity activity)
    {
        db = helper.getWritableDatabase();
        db.execSQL("update activity set " +
                        "name = ?," +
                        "organizer = ?," +
                        "time = ?," +
                        "content = ?," +
                        "where object_id = ?",
                new Object[]{
                        activity.getName(),
                        activity.getOrganizer(),
                        activity.getTime(),
                        activity.getContent(),
                        activity.getObjectId()
                });
    }

    public Activity find(String name)
    {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select object_id,name,type,time,description,picture from activity where name = ?",
                new String[]{name});
        Activity activity = null;
        if (cursor.moveToNext())
        {
            activity = new Activity(
                    cursor.getString(cursor.getColumnIndex("object_id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("organizer")),
                    cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("content")));
        }
        cursor.close();
        return activity;
    }

    public List<Activity> getActivityList(int start, int count)
    {
        List<Activity> list = new ArrayList<Activity>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from activity limit ?,?",
                new String[]{start+"",count+""});
        while(cursor.moveToNext())
        {
            list.add(new Activity(
                    cursor.getString(cursor.getColumnIndex("object_id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("organizer")),
                    cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("content"))));
        }

        cursor.close();

        return list;
    }

    public long getCount()
    {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(id) from activity",null);
        long result = 0;
        if (cursor.moveToNext())
        {
            result = cursor.getLong(0);
        }

        cursor.close();

        return result;
    }

    public int getMaxId()
    {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select max(id) from activity", null);
        int result = 0;
        while(cursor.moveToLast())
        {
            result = cursor.getInt(0);
        }

        cursor.close();

        return result;
    }
}
