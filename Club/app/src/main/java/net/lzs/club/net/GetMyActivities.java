package net.lzs.club.net;

import android.content.Context;

import net.lzs.club.model.Activity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by LEE on 2016/4/18.
 */
public class GetMyActivities
{
    public GetMyActivities(Context context, String userName, final SuccessCallback successCallback, final FailCallback failCallback)
    {
        BmobQuery<Activity> bmobQuery = new BmobQuery<Activity>();
        bmobQuery.addWhereEqualTo("belongs",userName);
        bmobQuery.findObjects(context, new FindListener<Activity>()
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
                if (failCallback != null)
                    failCallback.onFail(s);
            }
        });

    }

    public static interface SuccessCallback
    {
        void onSuccess(List<Activity> result);
    }
    public static interface FailCallback
    {
        void onFail(String errer);
    }
}
