package net.lzs.club.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.lzs.club.R;
import net.lzs.club.config.CacheType;
import net.lzs.club.config.Config;
import net.lzs.club.model.Club;
import net.lzs.club.net.DeleteData;
import net.lzs.club.net.UpdateUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LEE on 2016/4/14.
 */
public class MyClubIntroductionActivity extends Activity
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

    Club club;

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

        club = new Club();
        club.setObjectId(objectId);
        Log.i("MyClubIntroduction",club.getObjectId());

        likedClubs = Config.getCachedData(MyClubIntroductionActivity.this, CacheType.LIKEDCLUBS);

        btnCollect.setText("删除");

    }

    @OnClick(R.id.activity_clubintroduction_btn_collected)
    void click()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyClubIntroductionActivity.this);
        builder.setMessage("确定删除？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        Log.i("MyClubIntroduction",club.getObjectId());
                        new DeleteData(MyClubIntroductionActivity.this, club, CacheType.CLUB, new DeleteData.SuccessCallback()
                        {
                            @Override
                            public void onSuccess()
                            {
                                Toast.makeText(MyClubIntroductionActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                Config.cacheData(MyClubIntroductionActivity.this,"2",CacheType.FLAG);
                                MyClubIntroductionActivity.this.finish();
                            }
                        }, new DeleteData.FailCallback()
                        {
                            @Override
                            public void onFail(String error)
                            {
                                Toast.makeText(MyClubIntroductionActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("取消",null)
                .show();

    }
}
