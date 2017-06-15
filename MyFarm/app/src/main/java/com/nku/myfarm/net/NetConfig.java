package com.nku.myfarm.net;

/**
 * Created by Shane on 2017/3/30.
 */

/**
 * 用于配置网络相关的信息
 */
public class NetConfig {

    public static final String SERVER_IP = "47.92.119.236";

    public static final String SERVER_DATA_PORT = "11113";
    public static final String SERVER_DATA_URL = "http://" + SERVER_IP + ":" + SERVER_DATA_PORT + "/";

    public static final String SERVER_PICTURE_PORT = "11115";
    public static final String SERVER_PICTURE_URL = "http://" + SERVER_IP + ":" + SERVER_PICTURE_PORT + "/view?id=";

    public static final String HEADER_TYPE_KEY = "type";
    public static final String BODY_FILE_PART_NAME = "struct";

    //图片访问格式
    //http://47.92.119.236:11115/view?id=1496903910120575165

}
