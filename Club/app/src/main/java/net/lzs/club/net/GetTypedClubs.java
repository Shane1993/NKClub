package net.lzs.club.net;

import android.content.Context;

import net.lzs.club.model.Club;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by LEE on 2016/4/18.
 */
public class GetTypedClubs
{
    public GetTypedClubs(final Context context, String clubType, final SuccessCallback successCallback,
                   final FailCallback failCallback)
    {
        BmobQuery<Club> bmobQuery = new BmobQuery<Club>();
        bmobQuery.addWhereEqualTo("type",clubType);
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
                    failCallback.onFail();
            }
        });

//        new NetConnection(context, "test", new NetConnection.SuccessCallback()
//        {
//            @Override
//            public void onSuccess(String successResult)
//            {
//
//                try
//                {
//                    List<Club> list = new ArrayList<Club>();
//
//                    JSONObject object = new JSONObject(successResult);
//                    JSONArray arr = object.getJSONArray("clubs");
//                    for (int i = 0;i<arr.length();i++)
//                    {
//                        JSONObject jsonObject = arr.getJSONObject(i);
//                        String clubObjectId = jsonObject.getString(Config.OBJECTID);
//                        String clubName = jsonObject.getString(Config.CLUBNAME);
//                        String clubType = jsonObject.getString(Config.CLUBTYPE);
//                        String clubTime = jsonObject.getString(Config.CLUBTIME);
//                        String clubDescription = jsonObject.getString(Config.CLUBDESCRIPTION);
//                        Club club = new Club(clubObjectId,clubName,clubType,clubTime,clubDescription,"");
//                        list.add(club);
//                    }
//
//                    if (successCallback != null)
//                    {
//                        successCallback.onSuccess(list);
//                    }
//
//
//                } catch (JSONException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }, new NetConnection.FailCallback()
//        {
//            @Override
//            public void onFail(String failResult)
//            {
//                if(failCallback != null)
//                {
//                    failCallback.onFail();
//                }
//            }
//        },
//                "action", "getTypedClubs",
//                "clubType",clubType);
    }

    public static interface SuccessCallback
    {
        void onSuccess(List<Club> list);
    }

    public static interface FailCallback
    {
        void onFail();
    }
}
