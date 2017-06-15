package com.nku.myfarm.net;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.nku.myfarm.model_proto.ModelProto;

/**
 * Created by Shane on 2017/6/15.
 */

public class GetOrderById {

    public GetOrderById(String orderId, final Callback callback) {

        ModelProto.GetOrderByIdProto proto = ModelProto.GetOrderByIdProto.newBuilder()
                .setOrderid(orderId)
                .setReqtype(ModelProto.ReqType.tReqTypeGetOrderById)
                .build();

        new BasicNetConnection(proto, proto.getReqtype().name(), new BasicNetConnection.Callback() {
            @Override
            public void onResponse(byte[] rspProto) {
                if (callback == null) {
                    return;
                }


                try {
                    ModelProto.GetOrderByIdRspProto getOrderByIdRspProto  = ModelProto.GetOrderByIdRspProto.parseFrom(rspProto);
                    callback.onSuccess(getOrderByIdRspProto);
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
        public void onSuccess(ModelProto.GetOrderByIdRspProto getOrderByIdRspProto);

        public void onError(String errorStr);
    }


}
