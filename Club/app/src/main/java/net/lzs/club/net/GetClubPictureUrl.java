package net.lzs.club.net;

import android.content.Context;
import android.util.Log;

import net.lzs.club.model.Club;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by LEE on 2016/4/16.
 */
public class GetClubPictureUrl
{
    public GetClubPictureUrl(final Context context, String clubName, final SuccessCallback successCallback,
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

                    String objectId = jsonObject.getString("objectId");

                    BmobQuery<Club> query = new BmobQuery<Club>();
                    query.getObject(context, objectId, new GetListener<Club>()
                    {
                        @Override
                        public void onSuccess(Club club)
                        {

                            String clubPictureUrl = club.getIconUrl();

                            if(successCallback != null)
                            {
                                successCallback.onSuccess(clubPictureUrl);
                            }
                        }

                        @Override
                        public void onFailure(int i, String s)
                        {

                            if (failCallback != null)
                                failCallback.onFail(s);
                        }
                    });


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
                    failCallback.onFail(failResult);
                }
            }
        },
                "action", "getObjectId",
                "clubName",clubName);
    }

    public static interface SuccessCallback
    {
        void onSuccess(String clubPictureUrl);
    }

    public static interface FailCallback
    {
        void onFail(String error);
    }
}
