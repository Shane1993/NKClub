package com.nku.myfarm.net;

import android.telecom.Call;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.nku.myfarm.MyApplication;
import com.nku.myfarm.model_proto.ModelProto;
import com.nku.myfarm.util.ProtoUtil;

import java.io.File;

import static android.content.ContentValues.TAG;

/**
 * Created by Shane on 2017/6/14.
 */

public class BasicNetConnection {

    private static final String TAG = "BasicNetConnection";
    private static RequestQueue requestQueue = MyApplication.getRequestQueueInstance();


    private Callback callback;

    public BasicNetConnection(AbstractMessage reqProto, String typeName, final Callback callback) {

        File f = ProtoUtil.getFileFromProto(reqProto, typeName);

        MultipartRequest request = new MultipartRequest(NetConfig.SERVER_URL, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(callback != null) {
                    callback.onErrorResponse(error.toString());
                }
            }
        }, new Response.Listener<byte[]>() {
            @Override
            public void onResponse(byte[] response) {
//                Log.d(TAG, response);

                if ( callback != null) {
                    callback.onResponse(response);
                }
            }
        }, f, typeName);

        requestQueue.add(request);

    }

    /**
     * Callback用于向外提供异步通信的数据
     */
    public static interface Callback {
        public void onResponse(byte[] rspProtoByteArray);
        public void onErrorResponse(String errorStr);
    }
}
