package com.nku.myfarm.net;

import android.util.Log;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.nku.myfarm.model_proto.ModelProto;

import static android.content.ContentValues.TAG;

/**
 * Created by Shane on 2017/6/15.
 */

public class GetAllCategory {

    private static final String TAG = "GetAllCategory";

    public GetAllCategory(AbstractMessage proto, final Callback callback) {

        new BasicNetConnection(proto, ModelProto.ReqType.tReqTypeGetAllCategory.name(), new BasicNetConnection.Callback() {
            @Override
            public void onResponse(byte[] rspProto) {
                if (callback == null) {
                    return;
                }

                try {
                    ModelProto.GetAllCategoryRspProto getAllCategoryRspProto  = ModelProto.GetAllCategoryRspProto.parseFrom(rspProto);
                    callback.onSuccess(getAllCategoryRspProto);
                } catch (InvalidProtocolBufferException e) {
                    e.printStackTrace();
                    callback.onError(e.toString());
                }
            }

            @Override
            public void onErrorResponse(String errorStr) {
                if (callback != null) {
                    callback.onError(errorStr);
                }
            }
        });

    }

    public static interface Callback {
        public void onSuccess(ModelProto.GetAllCategoryRspProto getAllCategoryRspProto);

        public void onError(String errorStr);
    }


}
