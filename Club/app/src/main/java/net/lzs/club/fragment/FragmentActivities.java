package net.lzs.club.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.lzs.club.R;
import net.lzs.club.activity.LikedActivityContentActivity;
import net.lzs.club.activity.CreateActivityActivity;
import net.lzs.club.adapter.AdapterActivity;
import net.lzs.club.config.CacheType;
import net.lzs.club.config.Config;
import net.lzs.club.model.Activity;
import net.lzs.club.model.Club;
import net.lzs.club.net.GetActivities;
import net.lzs.club.net.GetMyClubs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LEE on 2016/4/14.
 */
public class FragmentActivities extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener
{
    private Context context;

    View view;
    ListView lvAvtivity;
    List<Activity> list;
    AdapterActivity adapterActivity;

    FloatingActionButton fabAdd,fabRefresh;

    ProgressDialog pd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_activities,null);

        initView();

        refreshList();

        return view;
    }

    private void initView()
    {
        context = getActivity();

        lvAvtivity = (ListView) view.findViewById(R.id.fragment_lv_activity);
        lvAvtivity.setOnItemClickListener(this);
        list = new ArrayList<Activity>();
        adapterActivity = new AdapterActivity(context,list);
        lvAvtivity.setAdapter(adapterActivity);

        fabAdd = (FloatingActionButton) view.findViewById(R.id.fragment_fab_add);
        fabAdd.setOnClickListener(this);
        fabRefresh = (FloatingActionButton) view.findViewById(R.id.fragment_fab_refresh);
        fabRefresh.setOnClickListener(this);

        pd = new ProgressDialog(context);
        pd.setMessage("刷新中。。。");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Activity activity = list.get(position);

        if(TextUtils.isEmpty(activity.getObjectId()))
        {
            Toast.makeText(context, "该活动不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(context, LikedActivityContentActivity.class);
        intent.putExtra(Config.OBJECTID,activity.getObjectId());
        intent.putExtra(Config.ACTIVITYNAME,activity.getName());
        intent.putExtra(Config.ACTIVITYORGANIZER,activity.getOrganizer());
        intent.putExtra(Config.ACTIVITYTIME,activity.getTime());
        intent.putExtra(Config.ACTIVITYCONTENT,activity.getContent());

        startActivity(intent);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.fragment_fab_add:

                new GetMyClubs(context, Config.getCachedData(context, CacheType.USERNAME), new GetMyClubs.SuccessCallback()
                {
                    @Override
                    public void onSuccess(List<Club> result)
                    {

                        if (result.size() == 0)
                        {
                            Toast.makeText(context, "请先创建自己的社团", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Intent intent = new Intent(context, CreateActivityActivity.class);

                            StringBuilder sb = new StringBuilder();
                            for(Club c : result)
                            {
                                sb.append(c.getName()+",");
                            }
                            intent.putExtra(Config.CLUBNAME,sb.toString());

                            startActivityForResult(intent,1);
                        }
                    }
                }, new GetMyClubs.FailCallback()
                {
                    @Override
                    public void onFail(String error)
                    {
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();

                    }
                });



                break;
            case R.id.fragment_fab_refresh:
                pd.show();
                refreshList();

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == 1)
        {
            refreshList();
        }
    }



    private void refreshList()
    {

        String startTime = "",endTime;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        endTime = simpleDateFormat.format(new Date());


        if (list.size() == 0)
        {
            startTime = "2016-01-01 00:00:00";
        }else
        {
            startTime = (list.get(0).getCreatedAt());
            try
            {
                Date d = simpleDateFormat.parse(startTime);
                d.setTime(d.getTime()+10000);
                startTime = simpleDateFormat.format(d);
            } catch (ParseException e)
            {
                e.printStackTrace();
            }
        }

        new GetActivities(context, startTime, endTime, new GetActivities.SuccessCallback()
        {
            @Override
            public void onSuccess(List<Activity> result)
            {

                if(pd.isShowing())
                    pd.dismiss();
                list.addAll(0,result);
                adapterActivity.notifyDataSetChanged();
            }
        }, new GetActivities.FailCallback()

        {
            @Override
            public void onFail(String errer)
            {
                if(pd.isShowing())
                    pd.dismiss();

                Toast.makeText(context, errer, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
