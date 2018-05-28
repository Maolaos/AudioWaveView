package com.jyy.audio_wave.util;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;

/**
 * 音频录制工具类
 */

public class AudioRecoderUtils {

    private static final String TAG = "AudioRecordManager";
    private volatile static AudioRecoderUtils INSTANCE;
    private MediaRecorder mediaRecorder;
    private String audioFileName = "";
    private RecordStatus recordStatus = RecordStatus.STOP;

    public enum RecordStatus {
        READY,
        START,
        STOP
    }

    private AudioRecoderUtils() {

    }

    public static AudioRecoderUtils getInstance() {
        if (INSTANCE == null) {
            synchronized (AudioRecoderUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AudioRecoderUtils();
                }
            }
        }
        return INSTANCE;
    }

    public void init(String audioFileName) {
        this.audioFileName = audioFileName;
        recordStatus = RecordStatus.READY;
    }

    public void startRecord() {

        if (recordStatus == RecordStatus.READY) {
            try {
                Log.d(TAG, "startRecord()");
                mediaRecorder = new MediaRecorder();
                //声源采样为麦克风
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                //音频编码方式为AMR
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
                //音频格式为AMR
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                //音频输出文件地址
                mediaRecorder.setOutputFile(audioFileName);

                mediaRecorder.prepare();

                mediaRecorder.start();
                recordStatus = RecordStatus.START;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "startRecord() invoke init first.");
        }
    }

    public String stopRecord() {
        if (recordStatus == RecordStatus.START) {
            String filePath = audioFileName;
            Log.d(TAG, " stopRecord()");
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            recordStatus = RecordStatus.STOP;
            audioFileName = null;
            return filePath;
        } else {
            Log.e(TAG, "stopRecord() invoke start first.");
            return null;
        }
    }

    public void cancelRecord() {
        if (recordStatus == RecordStatus.START) {
            Log.d(TAG, "cancelRecord()");
            String file = audioFileName;
            stopRecord();
            File file1 = new File(file);
            file1.delete();
        } else {
            Log.e(TAG, "startRecord() invoke start first.");
        }
    }

    /**
     * 返回值0~1之间
     *
     * @return
     */
    public float getMaxAmplitude() {
        if (recordStatus == RecordStatus.START) {
            return mediaRecorder.getMaxAmplitude() * 1.0f / 32768;
        }
        return 0;
    }


}
