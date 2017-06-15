package com.nku.myfarm.net;

/**
 * Created by Shane on 2017/6/15.
 */

import com.google.protobuf.InvalidProtocolBufferException;
import com.nku.myfarm.model_proto.ModelProto;
import com.google.protobuf.AbstractMessage;

public class Login {

    private Callback callback;

    public Login(String userName, String password, final Callback callback) {

        ModelProto.LoginProto proto = ModelProto.LoginProto.newBuilder()
                .setUsername(userName)
                .setPassword(password)
                .setReqtype(ModelProto.ReqType.tReqTypeLogin)
                .build();

        new BasicNetConnection(proto, proto.getReqtype().name(), new BasicNetConnection.Callback() {
            @Override
            public void onResponse(byte[] rspProto) {
                if (callback == null) {
                    return;
                }


                try {
                    ModelProto.LoginRspProto loginRspProto = ModelProto.LoginRspProto.parseFrom(rspProto);
                    callback.onSuccess(loginRspProto);
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
        public void onSuccess(ModelProto.LoginRspProto loginRspProto);

        public void onError(String errorStr);
    }


}

