package com.nku.myfarm.net;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.nku.myfarm.model_proto.ModelProto;

/**
 * Created by Shane on 2017/6/15.
 */

public class Register {

    private Callback callback;

    public Register(String userName, String password, final Callback callback) {

        ModelProto.RegisterProto proto = ModelProto.RegisterProto.newBuilder()
                .setUsername(userName)
                .setPassword(password)
                .setReqtype(ModelProto.ReqType.tReqTypeRegister)
                .build();

        new BasicNetConnection(proto, proto.getReqtype().name(), new BasicNetConnection.Callback() {
            @Override
            public void onResponse(byte[] rspProto) {
                if (callback == null) {
                    return;
                }


                try {
                    ModelProto.RegisterRspProto registerRspProto = ModelProto.RegisterRspProto.parseFrom(rspProto);
                    callback.onSuccess(registerRspProto);
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
        public void onSuccess(ModelProto.RegisterRspProto registerRspProto);
        public void onError(String errorStr);
    }




}
