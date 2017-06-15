package com.nku.myfarm.net;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.nku.myfarm.model_proto.ModelProto;

/**
 * Created by Shane on 2017/6/15.
 */

public class GetCartById {

    public GetCartById(AbstractMessage proto, final Callback callback) {

        new BasicNetConnection(proto, ModelProto.ReqType.tReqTypeGetCartById.name(), new BasicNetConnection.Callback() {
            @Override
            public void onResponse(byte[] rspProto) {
                if (callback == null) {
                    return;
                }


                try {
                    ModelProto.GetCartByIdRspProto getCartByIdRspProto = ModelProto.GetCartByIdRspProto.parseFrom(rspProto);
                    callback.onSuccess(getCartByIdRspProto);
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
        public void onSuccess(ModelProto.GetCartByIdRspProto getCartByIdRspProto);

        public void onError(String errorStr);
    }


}
