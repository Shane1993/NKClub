package net.lzs.club.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import net.lzs.club.R;
import net.lzs.club.config.CacheType;
import net.lzs.club.config.Config;
import net.lzs.club.model.User;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by LEE on 2016/4/14.
 */
public class LaunchActivity extends Activity
{

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            String token = Config.getCachedData(LaunchActivity.this, CacheType.TOKEN);
            if(TextUtils.isEmpty(token))
            {
                startActivity(new Intent(LaunchActivity.this,LoginActivity.class));
                LaunchActivity.this.finish();


            }else
            {
                User user = new User();
                user.setUsername(Config.getCachedData(LaunchActivity.this,CacheType.USERNAME));
                user.setPassword(Config.getCachedData(LaunchActivity.this,CacheType.PASSWORD));
                user.login(LaunchActivity.this, new SaveListener()
                {
                    @Override
                    public void onSuccess()
                    {
                        startActivity(new Intent(LaunchActivity.this,MainActivity.class));
                        LaunchActivity.this.finish();

                    }

                    @Override
                    public void onFailure(int i, String s)
                    {

                        Toast.makeText(LaunchActivity.this, s, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LaunchActivity.this,LoginActivity.class));
                        LaunchActivity.this.finish();

                    }
                });

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        handler.sendEmptyMessageDelayed(0,300);
    }

}
