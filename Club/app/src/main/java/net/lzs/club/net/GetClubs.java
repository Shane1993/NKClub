package net.lzs.club.net;

import android.content.Context;

import net.lzs.club.model.Club;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by LEE on 2016/4/18.
 */
public class GetClubs
{
    Context context;
    GetClubs.SuccessCallback successCallback;
    GetClubs.FailCallback failCallback;
    BmobQuery<Club> bmobQuery;
    int count = 0;
    int limit;

    public GetClubs(Context context, int limit, final SuccessCallback successCallback, final FailCallback failCallback)
    {
        this.context = context;
        this.successCallback = successCallback;
        this.failCallback = failCallback;
        this.limit = limit;

        bmobQuery = new BmobQuery<Club>();
        bmobQuery.setLimit(limit);

    }

    public void showNextClubs()
    {

        bmobQuery.setSkip(count * limit);
        bmobQuery.findObjects(context, new FindListener<Club>()
        {
            @Override
            public void onSuccess(List<Club> list)
            {
                if (successCallback != null)
                    successCallback.onSuccess(list);
                count++;

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
