package com.nku.myfarm.net;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.nku.myfarm.model_proto.ModelProto;

/**
 * Created by Shane on 2017/6/15.
 */

public class GetOrderByUser {

    public GetOrderByUser(String userName, final Callback callback) {

        ModelProto.GetOrderByUserProto proto = ModelProto.GetOrderByUserProto.newBuilder()
                .setUsername(userName)
                .setReqtype(ModelProto.ReqType.tReqTypeGetOrderByUser)
                .build();

        new BasicNetConnection(proto, proto.getReqtype().name(), new BasicNetConnection.Callback() {
            @Override
            public void onResponse(byte[] rspProto) {
                if (callback == null) {
                    return;
                }


                try {
                    ModelProto.GetOrderByUserRspProto getOrderByUserRspProto  = ModelProto.GetOrderByUserRspProto.parseFrom(rspProto);
                    callback.onSuccess(getOrderByUserRspProto);
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
        public void onSuccess(ModelProto.GetOrderByUserRspProto getOrderByUserRspProto );

        public void onError(String errorStr);
    }


}
