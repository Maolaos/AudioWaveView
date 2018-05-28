package com.jyy.audiowaveview;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * Created by jiaoyanan on 2018/5/10.
 */

public class AudioRecordView extends AppCompatImageView {

    private int mLastMotionX, mLastMotionY;
    //是否移动了
    private boolean isMoved;
    //长按的runnable
    private Runnable mLongPressRunnable=new mLongPressRunnable();;
    //移动的阈值
    private static final int TOUCH_SLOP = 20;
    //点击是否有效
    private boolean isValid;
    private OnAudioClickListener audioClickListener;

    public AudioRecordView(Context context) {
        super(context);

    }

    public AudioRecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = x;
                mLastMotionY = y;
                isMoved = false;
                postDelayed(mLongPressRunnable,200);
                break;
            case MotionEvent.ACTION_MOVE:
                if(isMoved)
                    break;
                if(Math.abs(mLastMotionX-x) > TOUCH_SLOP
                        || Math.abs(mLastMotionY-y) > TOUCH_SLOP) {
                    //移动超过阈值，则表示移动了
                    removeCallbacks(mLongPressRunnable);
                    if (isValid){
                        isValid=false;
                        isMoved = true;
                        if (audioClickListener!=null) {
                            audioClickListener.onAudioClick(false);
                        }

                    }

                }
                break;
            case MotionEvent.ACTION_UP:
                //释放
                removeCallbacks(mLongPressRunnable);
                if (isValid){
                    isValid=false;
                    if (audioClickListener!=null) {
                        audioClickListener.onAudioClick(false);
                    }

                }

                break;
        }
        return true;
    }
    class   mLongPressRunnable implements Runnable{
        @Override
        public void run() {
            isValid=true;
            if (audioClickListener!=null){
                audioClickListener.onAudioClick(true);
            }

        }
    }


    public interface  OnAudioClickListener{
        void onAudioClick(boolean isClick);
    }
    public void setAudioClickListener(OnAudioClickListener clickListener){
        this.audioClickListener=clickListener;
    }



}
