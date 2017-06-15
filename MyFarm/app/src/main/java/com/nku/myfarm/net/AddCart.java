package com.nku.myfarm.net;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.nku.myfarm.model_proto.ModelProto;

/**
 * Created by Shane on 2017/6/15.
 */

public class AddCart {

    public AddCart(AbstractMessage proto, final Callback callback) {

        new BasicNetConnection(proto, ModelProto.ReqType.tReqTypeAddCart.name(), new BasicNetConnection.Callback() {
            @Override
            public void onResponse(byte[] rspProtoStr) {
                if (callback == null) {
                    return;
                }


                try {
                    ModelProto.AddCartRspProto addCartRspProto  = ModelProto.AddCartRspProto.parseFrom(rspProtoStr);
                    callback.onSuccess(addCartRspProto);
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
        public void onSuccess(ModelProto.AddCartRspProto addCartRspProto );

        public void onError(String errorStr);
    }


}
