package com.nku.myfarm.net;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.nku.myfarm.model_proto.ModelProto;

/**
 * Created by Shane on 2017/6/15.
 */

public class GetProductByCategory {

    public GetProductByCategory(String categoryId, final Callback callback) {

        ModelProto.GetProductByCateGoryProto proto = ModelProto.GetProductByCateGoryProto.newBuilder()
                .setCategoryid(categoryId)
                .setReqtype(ModelProto.ReqType.tReqTypeGetProductByCategory)
                .build();

        new BasicNetConnection(proto, proto.getReqtype().name(), new BasicNetConnection.Callback() {
            @Override
            public void onResponse(byte[] rspProto) {
                if (callback == null) {
                    return;
                }


                try {
                    ModelProto.GetProductByCateGoryRspProto getProductByCateGoryRspProto  = ModelProto.GetProductByCateGoryRspProto.parseFrom(rspProto);
                    callback.onSuccess(getProductByCateGoryRspProto);
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
        public void onSuccess(ModelProto.GetProductByCateGoryRspProto getProductByCateGoryRspProto );

        public void onError(String errorStr);
    }


}
