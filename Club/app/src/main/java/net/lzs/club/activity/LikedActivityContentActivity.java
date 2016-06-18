package net.lzs.club.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.lzs.club.R;
import net.lzs.club.config.CacheType;
import net.lzs.club.config.Config;
import net.lzs.club.net.UpdateUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LEE on 2016/4/14.
 */
public class LikedActivityContentActivity extends Activity
{
    @Bind(R.id.activity_content_btn_collected)
    Button btnCollect;
    @Bind(R.id.activity_content_tv_name)
    TextView tvName;
    @Bind(R.id.activity_content_tv_organizer)
    TextView tvOrganizer;
    @Bind(R.id.activity_content_tv_time)
    TextView tvTime;
    @Bind(R.id.activity_content_tv_content)
    TextView tvContent;

    boolean startStatus = false,endStatus = false;
    String likedActivities;
    String objectId;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitycontent);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        tvName.setText(intent.getStringExtra(Config.ACTIVITYNAME));
        tvOrganizer.setText(intent.getStringExtra(Config.ACTIVITYORGANIZER));
        tvTime.setText(intent.getStringExtra(Config.ACTIVITYTIME));
        tvContent.setText(intent.getStringExtra(Config.ACTIVITYCONTENT));
        objectId = intent.getStringExtra(Config.OBJECTID);

        likedActivities = Config.getCachedData(this,CacheType.LIKEDACTIVITIES);
        if(likedActivities.contains(objectId))
        {
            btnCollect.setText("取消收藏");
        }
    }

    @OnClick(R.id.activity_content_btn_collected)
    void click()
    {
        endStatus = !endStatus;
        if(btnCollect.getText().equals("收藏"))
        {
            btnCollect.setText("取消收藏");
        }else
        {
            btnCollect.setText("收藏");
        }
    }

    @Override
    public void onBackPressed()
    {
        if (startStatus != endStatus)
        {
            if(!btnCollect.getText().equals("收藏"))
            {
                likedActivities += objectId+",";
            }else
            {
                likedActivities = likedActivities.replace(objectId+",","");
            }

            new UpdateUser(LikedActivityContentActivity.this, likedActivities, CacheType.LIKEDACTIVITIES, new UpdateUser.SuccessCallback()
            {
                @Override
                public void onSuccess()
                {
                    Config.cacheData(LikedActivityContentActivity.this, likedActivities, CacheType.LIKEDACTIVITIES);
                }
            }, new UpdateUser.FailCallback()
            {
                @Override
                public void onFail(String error)
                {
                    Toast.makeText(LikedActivityContentActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        }

        super.onBackPressed();
    }
}
