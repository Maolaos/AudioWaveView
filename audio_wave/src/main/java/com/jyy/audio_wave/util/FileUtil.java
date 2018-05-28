package com.jyy.audio_wave.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * 文件工具类
 */
public class FileUtil {
     private static  final String AUDIO_PATH="Audio";

    /**
     * 检查文件是否存在
     */
    public static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    /**
     * 获取SD卡根目录
     * @return
     */
    public  static String getSDPath(){
        File sdDir = null;
        //判断sd卡是否存在
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);
        if(sdCardExist) {
            //获取跟目录
            sdDir = Environment.getExternalStorageDirectory();
        }else {
            return null;
        }
        return sdDir.toString();
    }

    /**
     *  获取app包名目录
     * @param mContext
     * @return
     */
    private static  String getAppBasePath(Context mContext){
        return checkDirPath(getSDPath()+File.separator+mContext.getPackageName());
    }

    /**
     * 获取音频文件目录
     * @return
     */
    public static  String  getAppAudioPath(Context mContext){

        if (getSDPath()!=null){
            return checkDirPath(getAppBasePath(mContext)+File.separator+AUDIO_PATH+File.separator);
        }
        return null;
    }

}
