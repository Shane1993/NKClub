package net.lzs.club.net;

import android.content.Context;
import android.widget.Toast;

import net.lzs.club.config.CacheType;
import net.lzs.club.config.Config;
import net.lzs.club.model.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by LEE on 2016/4/19.
 */
public class UpdateUser
{
    public UpdateUser(final Context context, String data, CacheType type, final SuccessCallback successCallback, final FailCallback failCallback)
    {
        User newUser = new User();
        BmobUser user = BmobUser.getCurrentUser(context);
        switch (type)
        {
            case LIKEDCLUBS:
                newUser.setLikedClubs(data);
                newUser.setLikedActivities(Config.getCachedData(context,CacheType.LIKEDACTIVITIES));
                break;
            case LIKEDACTIVITIES:
                newUser.setLikedActivities(data);
                newUser.setLikedClubs(Config.getCachedData(context,CacheType.LIKEDCLUBS));
                break;
            default:
                return;
        }
        newUser.update(context, user.getObjectId(), new UpdateListener()
        {
            @Override
            public void onSuccess()
            {

                if (successCallback != null)
                    successCallback.onSuccess();
            }

            @Override
            public void onFailure(int i, String s)
            {

                if(failCallback != null)
                    failCallback.onFail(s);
            }
        });


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
