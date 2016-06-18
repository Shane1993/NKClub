package net.lzs.club.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.lzs.club.R;
import net.lzs.club.adapter.AdapterActivity;
import net.lzs.club.config.CacheType;
import net.lzs.club.config.Config;
import net.lzs.club.model.Activity;
import net.lzs.club.net.GetMyActivities;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LEE on 2016/4/19.
 */
public class MyActivitiesActivity extends android.app.Activity implements AdapterView.OnItemClickListener
{
    @Bind(R.id.activity_listactivities_lv)
    ListView lv;
    @Bind(R.id.activity_listactivities_tv)
    TextView title;

    List<Activity> list;
    AdapterActivity adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activities);

        ButterKnife.bind(this);

        title.setText("我的活动");

        list = new ArrayList<Activity>();
        adapter = new AdapterActivity(this,list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);

        new GetMyActivities(this,Config.getCachedData(this, CacheType.USERNAME),
                new GetMyActivities.SuccessCallback(){
                    @Override
                    public void onSuccess(List<Activity> result)
                    {
                        System.out.println(result);

                        list.addAll(result);
                        adapter.notifyDataSetChanged();
                    }
                },
                new GetMyActivities.FailCallback()
                {

                    @Override
                    public void onFail(String error)
                    {
                        Toast.makeText(MyActivitiesActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Activity activity = list.get(position);

        Intent intent = new Intent(MyActivitiesActivity.this, MyActivityContentActivity.class);
        intent.putExtra(Config.OBJECTID,activity.getObjectId());
        intent.putExtra(Config.ACTIVITYNAME,activity.getName());
        intent.putExtra(Config.ACTIVITYORGANIZER,activity.getOrganizer());
        intent.putExtra(Config.ACTIVITYTIME,activity.getTime());
        intent.putExtra(Config.ACTIVITYCONTENT,activity.getContent());
        startActivity(intent);

        MyActivitiesActivity.this.finish();
    }
}
