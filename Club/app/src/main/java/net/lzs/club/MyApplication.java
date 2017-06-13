package net.lzs.club;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import cn.bmob.v3.Bmob;

/**
 * Created by LEE on 2016/4/15.
 */
public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Bmob.initialize(this,"2b58ede2367fe0ad8dcf90b9dc6cf391");

        initImageLoader();
    }

    private void initImageLoader()
    {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
            .threadPriority(Thread.NORM_PRIORITY - 2)
            .denyCacheImageMultipleSizesInMemory()
            .diskCacheFileNameGenerator(new Md5FileNameGenerator())
            .diskCacheSize(50 * 1024 * 1024)
            .tasksProcessingOrder(QueueProcessingType.LIFO)
            .writeDebugLogs()
            .build();

        ImageLoader.getInstance().init(configuration);
    }
}
