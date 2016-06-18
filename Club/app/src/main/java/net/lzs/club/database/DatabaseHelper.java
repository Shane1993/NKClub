package net.lzs.club.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LEE on 2016/2/23.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    private static DatabaseHelper instance;

    private static final int VERSION = 1;
    private static final String DBNAME = "nkuclub.db";

    private static final String CREATE_USER_TABLE =
            "create table user (" +
                    "id integer primary key, " +
                    "object_id varchar(20), " +
                    "username varchar(50)," +
                    "password varchar(50)," +
                    "liked_activities varchar(255), " +
                    "liked_clubs varchar(255))";
    private static final String CREATE_CLUB_TABLE =
            "create table club (" +
                    "id integer primary key, " +
                    "object_id varchar(20)," +
                    "name varchar(50)," +
                    "type varchar(20)," +
                    "time varchar(20)," +
                    "description text," +
                    "belongs varchar(50)," +
                    "picture varchar(255))";
    private static final String CREATE_ACTIVITY_TABLE =
            "create table activity (" +
                    "id integer primary key, " +
                    "object_id varchar(20), " +
                    "name varchar(50)," +
                    "organizer varchar(50)," +
                    "time varchar(20)," +
                    "belongs varchar(50)," +
                    "content text)";

    public DatabaseHelper(Context context)
    {
        super(context, DBNAME, null, VERSION);
    }

    //Singleton
    public static DatabaseHelper getInstance(Context context)
    {
        if (instance == null)
        {
            synchronized (DatabaseHelper.class)
            {
                if (instance == null)
                {
                    instance = new DatabaseHelper(context.getApplicationContext());
                }
            }
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_CLUB_TABLE);
        db.execSQL(CREATE_ACTIVITY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
