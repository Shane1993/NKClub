package net.lzs.club.net;

import android.content.Context;

import net.lzs.club.config.CacheType;
import net.lzs.club.model.Activity;
import net.lzs.club.model.Club;

import cn.bmob.v3.listener.DeleteListener;

/**
 * Created by LEE on 2016/4/20.
 */
public class DeleteData
{
    public DeleteData(Context context,  Object object,CacheType type, final SuccessCallback successCallback, final FailCallback failCallback)
    {
        switch (type)
        {
            case ACTIVITY:

                ((Activity)object).delete(context, new DeleteListener()
                {
                    @Override
                    public void onSuccess()
                    {
                        if(successCallback != null)
                            successCallback.onSuccess();
                    }

                    @Override
                    public void onFailure(int i, String s)
                    {
                        if (failCallback != null)
                            failCallback.onFail(s);
                    }
                });

                break;
            case CLUB:

                ((Club)object).delete(context, new DeleteListener()
                {
                    @Override
                    public void onSuccess()
                    {
                        if(successCallback != null)
                            successCallback.onSuccess();
                    }

                    @Override
                    public void onFailure(int i, String s)
                    {
                        if (failCallback != null)
                            failCallback.onFail(s);
                    }
                });

                break;
        }
    }

    public static interface SuccessCallback
    {
        void onSuccess();
    }
    public static interface FailCallback
    {
        void onFail(String error);
    }


}
