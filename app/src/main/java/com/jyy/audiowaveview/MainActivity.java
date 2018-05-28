package com.jyy.audiowaveview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jyy.audio_wave.AudioRecoderUtils;
import com.jyy.audio_wave.WaveView;


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
                if (true){

                    mRecord.setBackgroundResource(R.mipmap.audio_recording);

                }else {
                    mRecord.setBackgroundResource(R.mipmap.audio_record);
                }
            }
        });

    }



}
