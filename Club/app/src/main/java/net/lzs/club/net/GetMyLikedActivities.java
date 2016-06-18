package net.lzs.club.net;

import android.content.Context;

import net.lzs.club.model.Activity;
import net.lzs.club.model.Club;

import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by LEE on 2016/4/18.
 */
public class GetMyLikedActivities
{
    public GetMyLikedActivities(Context context, String objectIds, final SuccessCallback successCallback, final FailCallback failCallback)
    {
        BmobQuery<Activity> bmobQuery = new BmobQuery<Activity>();
        bmobQuery.addWhereContainedIn("objectId", Arrays.asList(objectIds.split(",")));
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
