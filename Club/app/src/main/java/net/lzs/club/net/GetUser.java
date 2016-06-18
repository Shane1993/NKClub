package net.lzs.club.net;

import android.content.Context;

import net.lzs.club.model.Activity;
import net.lzs.club.model.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by LEE on 2016/4/18.
 */
public class GetUser
{
    public GetUser(Context context, String userName, final SuccessCallback successCallback, final FailCallback failCallback)
    {
        BmobQuery<User> bmobQuery = new BmobQuery<User>();
        bmobQuery.addWhereEqualTo("username",userName);
        bmobQuery.findObjects(context, new FindListener<User>()
        {
            @Override
            public void onSuccess(List<User> list)
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
        void onSuccess(List<User> result);
    }
    public static interface FailCallback
    {
        void onFail(String errer);
    }
}
