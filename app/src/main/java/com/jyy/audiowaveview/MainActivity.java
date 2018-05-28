package com.jyy.audiowaveview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jyy.audio_wave.WaveView;
import com.jyy.audio_wave.util.AudioRecoderUtils;
import com.jyy.audio_wave.util.FileUtil;

public class MainActivity extends AppCompatActivity {
    private AudioRecordView mRecord;
    private WaveView mWaveView;
    private AudioRecoderUtils mUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecord=findViewById(R.id.btn_main_record);
        mWaveView=findViewById(R.id.wv_main_show);

        mRecord.setAudioClickListener(new AudioRecordView.OnAudioClickListener() {
            @Override
            public void onAudioClick(boolean isClick) {
                if (isClick){
                    mRecord.setBackgroundResource(R.mipmap.audio_recording);
                    startRecord();
                }else {
                    mRecord.setBackgroundResource(R.mipmap.audio_record);
                    stopRecord();
                }
            }
        });

    }


    /**
     * 开始录音
     *
     * @return
     */
    private String startRecord() {

        //开启录音this
        mUtil = AudioRecoderUtils.getInstance();
        String filePath = FileUtil.getAppAudioPath(this) + System.currentTimeMillis() + ".amr";
        mUtil.init(filePath);
        mUtil.startRecord();
        mWaveView.startRecord();

        return filePath;
    }

    /**
     * 结束录音
     */
    private void stopRecord() {
        if (mUtil != null) {
            String filePath = mUtil.stopRecord();
        }
        mWaveView.stopRecord();


    }

}
