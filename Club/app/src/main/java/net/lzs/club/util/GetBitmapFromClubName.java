package net.lzs.club.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import net.lzs.club.config.Config;

import java.io.File;

/**
 * Created by LEE on 2016/4/18.
 */
public class GetBitmapFromClubName
{
    public static Bitmap getBitmap(String clubName)
    {
        FileUtils fileUtils = new FileUtils();
        File file = fileUtils.getFile(Config.APP_NAME,clubName+".jpg");

        if(file == null)
            return null;

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath(),options);

        return bmp;
    }
}
