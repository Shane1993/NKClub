package net.lzs.club.net;

import android.content.Context;

import net.lzs.club.model.Activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by LEE on 2016/4/16.
 */
public class GetActivities
{
    public GetActivities(Context context, String startTime, String endTime, final SuccessCallback successCallback,
                         final FailCallback failCallback)
    {

        BmobQuery<Activity> query = new BmobQuery<Activity>();
        List<BmobQuery<Activity>> and = new ArrayList<BmobQuery<Activity>>();

        //大于00：00：00
        BmobQuery<Activity> q1 = new BmobQuery<Activity>();
//        String start = "2015-05-01 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try
        {
            date = sdf.parse(startTime);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        q1.addWhereGreaterThanOrEqualTo("createdAt", new BmobDate(date));
        and.add(q1);

        //小于23：59：59
        BmobQuery<Activity> q2 = new BmobQuery<Activity>();
//        String end = "2015-05-01 23:59:59";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        try
        {
            date1 = sdf1.parse(endTime);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        q2.addWhereLessThanOrEqualTo("createdAt", new BmobDate(date1));
        and.add(q2);

        //添加复合与查询
        query.and(and);
        query.order("-createdAt");
        query.findObjects(context, new FindListener<Activity>()
        {
            @Override
            public void onSuccess(List<Activity> list)
            {
                if (successCallback != null)
                    successCallback.onSuccess(list);

            }

            @Override
            public void onError(int i, String s)
            {

                if(failCallback != null)
                    failCallback.onFail(s);
            }
        });

    }

    public static interface SuccessCallback
    {
        void onSuccess(List<Activity> list);
    }

    public static interface FailCallback
    {
        void onFail(String error);
    }
}
