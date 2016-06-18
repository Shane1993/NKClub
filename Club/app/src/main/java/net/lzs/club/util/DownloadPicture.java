package net.lzs.club.util;

import android.os.AsyncTask;

/**
 * Created by LEE on 2016/4/18.
 */
public class DownloadPicture
{
    public static void downFile(String url, String path, String file)
    {

        new AsyncTask<String, Void, Integer>()
        {
            @Override
            protected Integer doInBackground(String... params)
            {
                FileUtils fileUtils = new FileUtils();
                int result = fileUtils.downFile(params[0],params[1],params[2]);
                return result;
            }
        }.execute(url,path,file);
    }
}
