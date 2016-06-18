package net.lzs.club.net;

import android.content.Context;
import android.util.Log;

import net.lzs.club.config.Config;
import net.lzs.club.model.Club;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by LEE on 2016/4/16.
 */
public class GetClub
{
    public GetClub(final Context context, String clubName, final SuccessCallback successCallback,
                   final FailCallback failCallback)
    {

        new NetConnection(context, "test", new NetConnection.SuccessCallback()
        {
            @Override
            public void onSuccess(String successResult)
            {

                try
                {
                    JSONObject jsonObject = new JSONObject(successResult);

                    String clubObjectId = jsonObject.getString(Config.OBJECTID);
                    String clubName = jsonObject.getString(Config.CLUBNAME);
                    String clubType = jsonObject.getString(Config.CLUBTYPE);
                    String clubTime = jsonObject.getString(Config.CLUBTIME);
                    String clubDescription = jsonObject.getString(Config.CLUBDESCRIPTION);
                    String clubIconUri = jsonObject.getString(Config.CLUBICONURI);
                    Club club = new Club(clubObjectId,clubName,clubType,clubTime,clubDescription,"",clubIconUri);

                    if (successCallback != null)
                    {
                        successCallback.onSuccess(club);
                    }


                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new NetConnection.FailCallback()
        {
            @Override
            public void onFail(String failResult)
            {
                if(failCallback != null)
                {
                    failCallback.onFail();
                }
            }
        },
                "action", "getClub",
                "clubName",clubName);
    }

    public static interface SuccessCallback
    {
        void onSuccess(Club club);
    }

    public static interface FailCallback
    {
        void onFail();
    }
}
