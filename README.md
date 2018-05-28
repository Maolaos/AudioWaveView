# AudioWaveView
录音音频，根据麦克风声音大小，自动变化的上下浮动波纹效果

1.xml 示例

<com.jyy.audio_wave.WaveView

      android:layout_width="250dp" 
      android:layout_height="60dp"  
      android:layout_centerHorizontal="true"
      waveview:audioCenterLineColor="#333333" 
      waveview:audioWaveColor="#333333" 
      waveview:audioWaveWidth="1dp"
      waveview:audioCenterLineHeight="0.5dp"
      waveview:audioSpaceWidth="8dp" 
      waveview:audioWaveHeight="12dp"
      waveview:audioCenterLine="true"/>  
      
       
 2.属性表示
 
       <!--音频波纹属性-->
    <declare-styleable name="AudioWaveView">
        <!--音频波纹颜色-->
        <attr name="audioWaveColor" format="color"/>
        <!--音频波纹宽度-->
        <attr name="audioWaveWidth" format="dimension"/>
        <!--音频波纹最低高度-->
        <attr name="audioWaveHeight" format="dimension"/>
        <!--音频波纹空隙宽度-->
        <attr name="audioSpaceWidth" format="dimension" />
        <!--音频中心线条颜色-->
        <attr name="audioCenterLineColor" format="color" />
        <!--音频中心线条高度-->
        <attr name="audioCenterLineHeight" format="dimension" />
        <!--是否有中心线条-->
        <attr name="audioCenterLine" format="boolean" />
    </declare-styleable>
       
  3.引用
  
    step1：
    
    Add it in your root build.gradle at the end of repositories:
    
    
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
      
    step2：
    
    in your app build.gradle add the dependency
    
    dependencies {
	        implementation 'com.github.Maolaos:AudioWaveView:v1.0.1'
	}
    
       
      
