package com.nku.myfarm.util;

import android.os.Environment;

import com.google.protobuf.AbstractMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Shane on 2017/6/14.
 */

public class ProtoUtil {


    private static final String SDCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";

    /**
     * 通过Proto创建File，其中typeName是类型的名字，type包括ReqType和RspType
     * @param proto
     * @param typeName
     * @return
     */
    public static File getFileFromProto(AbstractMessage proto, String typeName) {
        File f = new File(SDCardRoot + typeName);
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(f);
            proto.writeTo(fos);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {

                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return f;
    }
}
