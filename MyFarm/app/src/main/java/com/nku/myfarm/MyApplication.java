package com.nku.myfarm;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by Shane on 2017/3/27.
 */

public class MyApplication extends Application {

    private static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        //ImageLoader全局初始化
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(configuration);


        //Volley的RequestQueue全局初始化
        requestQueue = Volley.newRequestQueue(getApplicationContext());


    }


    /**
     * 这里不用单例模式是因为，能够保证onCreate能够先执行，所以requestQueue一定被初始化了
     * @return
     */
    public static RequestQueue getRequestQueueInstance() {

        return requestQueue;
    }

}
