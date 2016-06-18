package net.lzs.club.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.lzs.club.R;
import net.lzs.club.config.CacheType;
import net.lzs.club.config.Config;
import net.lzs.club.net.DeleteData;
import net.lzs.club.net.UpdateUser;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LEE on 2016/4/14.
 */
public class MyActivityContentActivity extends Activity
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

    net.lzs.club.model.Activity activity;

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

        activity = new net.lzs.club.model.Activity();
        activity.setObjectId(objectId);

        btnCollect.setText("删除");

    }

    @OnClick(R.id.activity_content_btn_collected)
    void click()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MyActivityContentActivity.this);
        builder.setMessage("确定删除？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        new DeleteData(MyActivityContentActivity.this, activity, CacheType.ACTIVITY, new DeleteData.SuccessCallback()
                        {
                            @Override
                            public void onSuccess()
                            {
                                Toast.makeText(MyActivityContentActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                Config.cacheData(MyActivityContentActivity.this,"2",CacheType.FLAG);
                                MyActivityContentActivity.this.finish();

                            }
                        }, new DeleteData.FailCallback()
                        {
                            @Override
                            public void onFail(String error)
                            {
                                Toast.makeText(MyActivityContentActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("取消",null)
                .show();


    }

}
