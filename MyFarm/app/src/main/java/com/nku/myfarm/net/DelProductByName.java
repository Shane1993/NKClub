package com.nku.myfarm.net;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.nku.myfarm.model_proto.ModelProto;

/**
 * Created by Shane on 2017/6/15.
 */

public class DelProductByName {

    public DelProductByName(String proName, final Callback callback) {

        ModelProto.DelProductByNameProto proto = ModelProto.DelProductByNameProto.newBuilder()
                .setProname(proName)
                .setReqtype(ModelProto.ReqType.tReqTypeDelProductByName)
                .build();

        new BasicNetConnection(proto, proto.getReqtype().name(), new BasicNetConnection.Callback() {
            @Override
            public void onResponse(byte[] rspProto) {
                if (callback == null) {
                    return;
                }


                try {
                    ModelProto.DelProductByNameRspProto delProductByNameRspProto = ModelProto.DelProductByNameRspProto.parseFrom(rspProto);
                    callback.onSuccess(delProductByNameRspProto);
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
        public void onSuccess(ModelProto.DelProductByNameRspProto delProductByNameRspProto);

        public void onError(String errorStr);
    }
}
