package com.jyy.audio_wave;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


import com.jyy.audio_wave.util.AudioRecoderUtils;
import com.jyy.audio_wave.util.ThreadPoolUtil;

import java.util.LinkedList;
import java.util.List;


/**
 *
 * Created by jiaoyanan on 2018/5/22.
 * 语音录制的动画效果
 *
 */
public class WaveView extends View {
    private static final String TAG = WaveView.class.getSimpleName();
    //画笔
    private Paint paint;
    //中间横线高度
    private float centerLineHeight;
    //中间线的颜色
    private int centerLineColor;
    //矩形波纹颜色
    private int waveColor;
    //矩形波纹宽度
    private float waveWidth;
    //矩形波纹高度
    private float waveHeight;

    //间距宽度
    private float spaceWidth;
    //默认矩形波纹的宽度
    private int WAVE_W = 10;
    //默认最小的矩形线高，
    private int MIN_WAVE_H = 20;
    //默认最间距宽度
    private int MIN_SPACE_H=15;
    //默认竖纹数量
    private int WAVE_NUM=12;
    //默认中心线高度
    private int CENTER_LINE_H=5;
    //默认矩形波纹的高度，总共12个矩形
    private int[] DEFAULT_WAVE_HEIGHT = {MIN_WAVE_H+1, MIN_WAVE_H+2, MIN_WAVE_H+2, MIN_WAVE_H+3,
            MIN_WAVE_H+1, MIN_WAVE_H, MIN_WAVE_H, MIN_WAVE_H+2, MIN_WAVE_H+3, MIN_WAVE_H+2,MIN_WAVE_H,MIN_WAVE_H+1};
    //更新的波纹高度
    private LinkedList<Integer> mWaveList = new LinkedList<>();
    private LinkedList<Integer> list = new LinkedList<>();
    //波纹矩形数据
    private RectF rectWave = new RectF();
    //中心线条数据
    private RectF rectLine=new RectF();
    //100ms更新一次
    private static final int UPDATE_INTERVAL_TIME = 100;
    //第一次高度
    private boolean isStart = false;
    // 定时更新线程
    private Runnable mRunable;
    //是否显示中间横线
    private boolean isCenterLine=true;

    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        resetList(list, DEFAULT_WAVE_HEIGHT);

        mRunable = new Runnable() {
            @Override
            public void run() {
                while (isStart){
                    refreshElement();
                    try {
                        Thread.sleep(UPDATE_INTERVAL_TIME);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }
            }
        };
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.AudioWaveView);
        waveColor = mTypedArray.getColor(R.styleable.AudioWaveView_audioWaveColor, Color.parseColor("#ff9c00"));
        waveWidth = mTypedArray.getDimension(R.styleable.AudioWaveView_audioWaveWidth, WAVE_W);
        waveHeight=mTypedArray.getDimension(R.styleable.AudioWaveView_audioWaveHeight,MIN_WAVE_H);
        spaceWidth=mTypedArray.getDimension(R.styleable.AudioWaveView_audioSpaceWidth, MIN_SPACE_H);
        centerLineColor=mTypedArray.getColor(R.styleable.AudioWaveView_audioCenterLineColor,Color.parseColor("#ff9c00"));
        centerLineHeight=mTypedArray.getDimension(R.styleable.AudioWaveView_audioCenterLineHeight,CENTER_LINE_H);
        isCenterLine=mTypedArray.getBoolean(R.styleable.AudioWaveView_audioCenterLine,true);
        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int line_width =  getWidth();
        int line_height = getHeight() / 2;
        int wave_width=  (int)(getWidth() / 2+(WAVE_NUM*waveWidth+(WAVE_NUM+1)*spaceWidth)/2);
        int wave_height=  getHeight() / 2;
        //横线
        rectLine.left = 0;
        rectLine.top =line_height-centerLineHeight;
        rectLine.right =line_width;
        rectLine.bottom = line_height+centerLineHeight;;
        if(isCenterLine) {
            paint.setColor(centerLineColor);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(line_width);
            paint.setAntiAlias(true);
            canvas.drawRect(rectLine, paint);
        }



        //更新波纹矩形
        paint.setColor(waveColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(waveWidth);
        paint.setAntiAlias(true);
        for(int i = 0 ; i < WAVE_NUM ; i++) {


            //矩形
            rectWave.left = wave_width - ( i * (waveWidth+spaceWidth) +  waveWidth+spaceWidth );
            rectWave.top = wave_height - list.get(i) * waveWidth / 2;
            rectWave.right =wave_width  -(  i * (waveWidth+spaceWidth)  + spaceWidth);
            rectWave.bottom = wave_height + list.get(i) * waveWidth / 2;
            canvas.drawRoundRect(rectWave, 6, 6, paint);
        }
    }

    private synchronized void refreshElement() {
        float maxAmp = AudioRecoderUtils.getInstance().getMaxAmplitude();

        int waveH =(int) (waveHeight + Math.round(maxAmp * (6*waveHeight) ));
        list.add(0, waveH);
        list.removeLast();
    }



    public synchronized void startRecord(){
        isStart = true;
        ThreadPoolUtil.getInstance().getCachedPools().execute(mRunable);
    }

    public synchronized void stopRecord(){
        isStart = false;
        mWaveList.clear();
        resetList(list, DEFAULT_WAVE_HEIGHT);
        postInvalidate();
    }

    private void resetList(List list, int[] array) {
        list.clear();
        for(int i = 0 ; i < array.length; i++ ){
            list.add(array[i]);
        }
    }
}
