package net.lzs.club.net;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by LEE on 2016/4/16.
 */
public class GetClubsName
{
    public GetClubsName(Context context, final SuccessCallback successCallback, final FailCallback failCallback)
    {
        new NetConnection(context, "test", new NetConnection.SuccessCallback()
        {
            @Override
            public void onSuccess(String successResult)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(successResult);
                    String clubs = jsonObject.getString("clubs");


                    if (successCallback != null)
                        successCallback.onSuccess(clubs);


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
                if (failCallback != null)
                    failCallback.onFail();
            }
        },
                "action", "getClubsName");
    }


    public static interface SuccessCallback
    {
        void onSuccess(String clubs);
    }

    public static interface FailCallback
    {
        void onFail();
    }
}
