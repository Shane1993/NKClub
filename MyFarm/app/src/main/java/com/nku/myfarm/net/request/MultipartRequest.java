package com.nku.myfarm.net.request;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.protobuf.InvalidProtocolBufferException;
import com.nku.myfarm.model_proto.ModelProto;
import com.nku.myfarm.net.NetConfig;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultipartRequest extends Request<byte[]> {

    private static final String TAG = "MultipartRequest";
  
    private MultipartEntity entity = new MultipartEntity();
  
    private final Response.Listener<byte[]> mListener;
  
    private List<File> mFileParts;
    private String mFilePartName;  
    private Map<String, String> mParams;

    private String mHeaderTypeName;

    public MultipartRequest(String url, Response.ErrorListener errorListener,
                            Response.Listener<byte[]> listener, File file, String headerTypeName
                            ) {
        this(url, errorListener, listener, file, headerTypeName, null);
    }
    public MultipartRequest(String url, Response.ErrorListener errorListener,
                            Response.Listener<byte[]> listener, File file, String headerTypeName,
                            Map<String, String> params) {
        this(url, errorListener, listener, NetConfig.BODY_FILE_PART_NAME, file, headerTypeName, params);
    }

    /** 
     * 单个文件 
     * @param url 
     * @param errorListener 
     * @param listener 
     * @param filePartName 
     * @param file 
     * @param params 
     */  
    public MultipartRequest(String url, Response.ErrorListener errorListener,  
            Response.Listener<byte[]> listener, String filePartName, File file, String headerTypeName,
            Map<String, String> params) {  
        super(Method.POST, url, errorListener);  
  
        mFileParts = new ArrayList<File>();
        if (file != null) {  
            mFileParts.add(file);  
        }  
        mFilePartName = filePartName;  
        mListener = listener;  
        mParams = params;

        mHeaderTypeName = headerTypeName;

        buildMultipartEntity();
    }
    /** 
     * 多个文件，对应一个key 
     * @param url 
     * @param errorListener 
     * @param listener 
     * @param filePartName 
     * @param files 
     * @param params 
     */  
    public MultipartRequest(String url, Response.ErrorListener errorListener,  
            Response.Listener<byte[]> listener, String filePartName,
            List<File> files, String headerTypeName, Map<String, String> params) {
        super(Method.POST, url, errorListener);  
        mFilePartName = filePartName;  
        mListener = listener;  
        mFileParts = files;  
        mParams = params;

        mHeaderTypeName = headerTypeName;


        buildMultipartEntity();  
    }  
  
    private void buildMultipartEntity() {  
        if (mFileParts != null && mFileParts.size() > 0) {  
            for (File file : mFileParts) {  
                entity.addPart(mFilePartName, new FileBody(file));
            }  
            long l = entity.getContentLength();  
            Log.d(TAG, mFileParts.size()+"个，长度："+l);
        }  
  
        try {  
            if (mParams != null && mParams.size() > 0) {  
                for (Map.Entry<String, String> entry : mParams.entrySet()) {  
                    entity.addPart(  
                            entry.getKey(),  
                            new StringBody(entry.getValue(), Charset
                                    .forName("UTF-8")));  
                }  
            }  
        } catch (UnsupportedEncodingException e) {
            VolleyLog.e("UnsupportedEncodingException");
        }  
    }  
  
    @Override  
    public String getBodyContentType() {  
        return entity.getContentType().getValue();
    }  
  
    @Override  
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {  
            entity.writeTo(bos);  
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");  
        }  
        return bos.toByteArray();  
    }  
  
    @Override  
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
//        Log.d(TAG, "parseNetworkResponse");
        if (VolleyLog.DEBUG) {  
            if (response.headers != null) {  
                for (Map.Entry<String, String> entry : response.headers  
                        .entrySet()) {  
                    VolleyLog.d(entry.getKey() + "=" + entry.getValue());  
                }  
            }  
        }

//        String parsed;
//        try {
//            parsed = new String(response.data,
//                    HttpHeaderParser.parseCharset(response.headers));
//        } catch (UnsupportedEncodingException e) {
//            parsed = new String(response.data);
//        }

        return Response.success(response.data,
                HttpHeaderParser.parseCacheHeaders(response));  
    }  
  
  
    /* 
     * (non-Javadoc) 
     *  
     * @see com.android.volley.Request#getHeaders() 
     */  
    @Override  
    public Map<String, String> getHeaders() throws AuthFailureError {  
//        VolleyLog.d("getHeaders");
        Map<String, String> headers = super.getHeaders();  
  
        if (headers == null || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }


        //在网络包的Header中添加信息
        if(!TextUtils.isEmpty(mHeaderTypeName)) {
            headers.put(NetConfig.HEADER_TYPE_KEY, mHeaderTypeName);
        }

  
  
        return headers;  
    }  
  
    @Override  
    protected void deliverResponse(byte[] response) {
        mListener.onResponse(response);  
    }  
}