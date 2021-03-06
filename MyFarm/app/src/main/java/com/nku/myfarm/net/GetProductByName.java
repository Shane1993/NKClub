package com.nku.myfarm.net;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.nku.myfarm.model_proto.ModelProto;

/**
 * Created by Shane on 2017/6/15.
 */

public class GetProductByName {

    private Callback callback;

    public GetProductByName(String proName, final Callback callback) {

        ModelProto.GetProductByNameProto proto = ModelProto.GetProductByNameProto.newBuilder()
                .setProname(proName)
                .setReqtype(ModelProto.ReqType.tReqTypeGetProductByName)
                .build();

        new BasicNetConnection(proto, proto.getReqtype().name(), new BasicNetConnection.Callback() {
            @Override
            public void onResponse(byte[] rspProto) {
                if (callback == null) {
                    return;
                }


                try {
                    ModelProto.GetProductByNameRspProto getProductByNameRspProto = ModelProto.GetProductByNameRspProto.parseFrom(rspProto);
                    callback.onSuccess(getProductByNameRspProto);
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
        public void onSuccess(ModelProto.GetProductByNameRspProto getProductByNameRspProto);

        public void onError(String errorStr);
    }

}
