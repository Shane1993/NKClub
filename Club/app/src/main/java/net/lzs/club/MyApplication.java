package net.lzs.club;

import android.app.Application;
import android.util.Log;

import cn.bmob.v3.Bmob;

/**
 * Created by LEE on 2016/4/15.
 */
public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Bmob.initialize(this,"2b58ede2367fe0ad8dcf90b9dc6cf391");
    }
}
