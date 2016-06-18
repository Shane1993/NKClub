package net.lzs.club.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.lzs.club.model.User;

/**
 * Created by LEE on 2016/4/15.
 */
public class UserDAO
{
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public UserDAO(Context context)
    {
        helper = DatabaseHelper.getInstance(context);
    }

    public void add(User user)
    {
        db = helper.getWritableDatabase();
        db.execSQL("insert into user(" +
                "liked_activities," +
                "liked_clubs) values (?,?)",
                new Object[]{
                        user.getLikedActivities(),
                        user.getLikedClubs()});
    }

    public User find(String userName)
    {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select object_id,username,password,my_activities," +
                "my_clubs,liked_activities,liked_clubs" +
                "from user where username = ?",
                new String[]{userName});
        User user = null;
        if(cursor.moveToNext())
        {
            user =  new User(
                    cursor.getString(cursor.getColumnIndex("liked_activities")),
                    cursor.getString(cursor.getColumnIndex("liked_clubs"))
                    );
        }

        cursor.close();

        return user;
    }

    public void update(User user)
    {
        db = helper.getWritableDatabase();
        db.execSQL("update user set my_activities = ?,my_clubs = ?,liked_activities = ?,liked_clubwhere = ? object_id = ?",
                new Object[]{
                        user.getLikedActivities(),
                        user.getLikedClubs()});
    }



}
