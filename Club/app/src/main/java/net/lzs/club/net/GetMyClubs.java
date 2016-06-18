package net.lzs.club.net;

import android.content.Context;
import android.util.Log;

import net.lzs.club.model.Club;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by LEE on 2016/4/18.
 */
public class GetMyClubs
{
    public GetMyClubs(Context context, String userName, final SuccessCallback successCallback, final FailCallback failCallback)
    {
        BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
        bmobQuery.addWhereEqualTo("belongs",userName);
        bmobQuery.findObjects(context, new FindListener<Club>()
        {
            @Override
            public void onSuccess(List<Club> list)
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
        void onSuccess(List<Club> result);
    }
    public static interface FailCallback
    {
        void onFail(String errer);
    }
}
