package com.nku.myfarm.net;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.nku.myfarm.model_proto.ModelProto;

/**
 * Created by Shane on 2017/6/15.
 */

public class AddOrder {

    public AddOrder(String userName, float amounts, String proId, String proName, float totPrice, final Callback callback) {

        ModelProto.AddOrderProto proto = ModelProto.AddOrderProto.newBuilder()
                .setUsername(userName)
                .setAmounts(amounts)
                .setProid(proId)
                .setProname(proName)
                .setTotprice(totPrice)
                .setReqtype(ModelProto.ReqType.tReqTypeAddOrder)
                .build();


        new BasicNetConnection(proto, proto.getReqtype().name(), new BasicNetConnection.Callback() {
            @Override
            public void onResponse(byte[] rspProto) {
                if (callback == null) {
                    return;
                }


                try {
                    ModelProto.AddOrderRspProto addOrderRspProto = ModelProto.AddOrderRspProto.parseFrom(rspProto);
                    callback.onSuccess(addOrderRspProto);
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
        public void onSuccess(ModelProto.AddOrderRspProto addOrderRspProto);

        public void onError(String errorStr);
    }


}
