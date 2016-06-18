package net.lzs.club.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
public class LikedClubIntroductionActivity extends Activity
{
    @Bind(R.id.activity_clubintroduction_btn_collected)
    Button btnCollect;
    @Bind(R.id.activity_clubintroduction_tv_name)
    TextView tvName;
    @Bind(R.id.activity_clubintroduction_tv_type)
    TextView tvType;
    @Bind(R.id.activity_clubintroduction_tv_time)
    TextView tvTime;
    @Bind(R.id.activity_clubintroduction_tv_description)
    TextView tvDescription;

    boolean startStatus = false;
    boolean endStatus = false;

    String likedClubs;
    String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubintroduction);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        tvName.setText(intent.getStringExtra(Config.CLUBNAME));
        tvType.setText(intent.getStringExtra(Config.CLUBTYPE));
        tvTime.setText(intent.getStringExtra(Config.CLUBTIME));
        tvDescription.setText(intent.getStringExtra(Config.CLUBDESCRIPTION));
        objectId = intent.getStringExtra(Config.OBJECTID);

        likedClubs = Config.getCachedData(LikedClubIntroductionActivity.this, CacheType.LIKEDCLUBS);

        if(likedClubs.contains(objectId))
        {
            btnCollect.setText("取消关注");
        }

    }

    @OnClick(R.id.activity_clubintroduction_btn_collected)
    void click()
    {
        endStatus = !endStatus;
        if(btnCollect.getText().equals("关注"))
        {
            btnCollect.setText("取消关注");
        }else
        {
            btnCollect.setText("关注");
        }
    }

    @Override
    public void onBackPressed()
    {
        if (startStatus != endStatus)
        {
            if(!btnCollect.getText().equals("关注"))
            {
                likedClubs += objectId+",";
            }else
            {
                likedClubs = likedClubs.replace(objectId+",","");
            }

            new UpdateUser(LikedClubIntroductionActivity.this, likedClubs, CacheType.LIKEDCLUBS, new UpdateUser.SuccessCallback()
            {
                @Override
                public void onSuccess()
                {
                    Config.cacheData(LikedClubIntroductionActivity.this, likedClubs, CacheType.LIKEDCLUBS);
                }
            }, new UpdateUser.FailCallback()
            {
                @Override
                public void onFail(String error)
                {
                    Toast.makeText(LikedClubIntroductionActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        }

        super.onBackPressed();
    }
}
