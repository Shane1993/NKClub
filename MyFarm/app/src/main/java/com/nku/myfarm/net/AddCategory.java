package com.nku.myfarm.net;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.nku.myfarm.model_proto.ModelProto;

/**
 * Created by Shane on 2017/6/15.
 */

public class AddCategory {


    public AddCategory(AbstractMessage proto, final Callback callback) {

        new BasicNetConnection(proto, ModelProto.ReqType.tReqTypeAddCategory.name(), new BasicNetConnection.Callback() {
            @Override
            public void onResponse(byte[] rspProto) {
                if (callback == null) {
                    return;
                }


                try {
                    ModelProto.AddCategoryRspProto addCategoryRspProto  = ModelProto.AddCategoryRspProto.parseFrom(rspProto);
                    callback.onSuccess(addCategoryRspProto);
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
        public void onSuccess(ModelProto.AddCategoryRspProto addCategoryRspProto);

        public void onError(String errorStr);
    }


}
