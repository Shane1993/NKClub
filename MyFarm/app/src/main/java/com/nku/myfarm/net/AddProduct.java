package com.nku.myfarm.net;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.nku.myfarm.model_proto.ModelProto;

/**
 * Created by Shane on 2017/6/15.
 */

public class AddProduct {

    private Callback callback;

    public AddProduct(String proName, float price, String picName, String categoryId, final Callback callback) {

        ModelProto.AddProductProto proto = ModelProto.AddProductProto.newBuilder()
                .setProname(proName)
                .setPrice(price)
                .setPicname(picName)
                .setCategoryid(categoryId)
                .setReqtype(ModelProto.ReqType.tReqTypeAddProduct)
                .build();

        new BasicNetConnection(proto, proto.getReqtype().name(), new BasicNetConnection.Callback() {
            @Override
            public void onResponse(byte[] rspProto) {
                if (callback == null) {
                    return;
                }


                try {
                    ModelProto.AddProductRspProto addProductRspProto = ModelProto.AddProductRspProto.parseFrom(rspProto);
                    callback.onSuccess(addProductRspProto);
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
        public void onSuccess(ModelProto.AddProductRspProto addProductRspProto);

        public void onError(String errorStr);
    }

}
