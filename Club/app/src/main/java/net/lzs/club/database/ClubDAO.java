package net.lzs.club.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.lzs.club.model.Club;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LEE on 2016/4/15.
 */
public class ClubDAO
{
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public ClubDAO(Context context)
    {
        helper = DatabaseHelper.getInstance(context);
    }

    public void add(Club club)
    {
        db = helper.getWritableDatabase();
        db.execSQL("insert into club(" +
                        "object_id," +
                        "name," +
                        "type," +
                        "time," +
                        "description," +
                        "belongs)values (?,?,?,?,?,?,?)",
                new Object[]{
                        club.getObjectId(),
                        club.getName(),
                        club.getType(),
                        club.getTime(),
                        club.getDescription(),
                        club.getBelongs(),
                });
    }

    public void update(Club club)
    {
        db = helper.getWritableDatabase();
        db.execSQL("update club set " +
                        "name = ?," +
                        "type = ?," +
                        "time = ?," +
                        "description = ?," +
                        "belongs = ?," +
                        "where object_id = ?",
                new Object[]{
                        club.getName(),
                        club.getType(),
                        club.getTime(),
                        club.getDescription(),
                        club.getBelongs(),
                });
    }

    public Club find(String name)
    {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select object_id,name,type,time,description,belongs,picture from club where name = ?",
                new String[]{name});
        Club club = null;
        if (cursor.moveToNext())
        {
            club = new Club(
                    cursor.getString(cursor.getColumnIndex("object_id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getString(cursor.getColumnIndex("belongs")));
        }
        cursor.close();
        return club;
    }

    public List<Club> getClubList(int start,int count)
    {
        List<Club> list = new ArrayList<Club>();
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from club limit ?,?",
                new String[]{start+"",count+""});
        while(cursor.moveToNext())
        {
            list.add(new Club(
                    cursor.getString(cursor.getColumnIndex("object_id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getString(cursor.getColumnIndex("belongs"))));
        }

        cursor.close();

        return list;
    }

    public long getCount()
    {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(id) from club",null);
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
        Cursor cursor = db.rawQuery("select max(id) from club", null);
        int result = 0;
        while(cursor.moveToLast())
        {
            result = cursor.getInt(0);
        }

        cursor.close();

        return result;
    }

}
