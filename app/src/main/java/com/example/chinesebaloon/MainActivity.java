package com.example.chinesebaloon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private Draw2D draw2d;
    private SensorManager sm;
    private Sensor s;
    private SensorEventListener sv;
    //MediaPlayer mPlayer1,mPlayer2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mPlayer1= MediaPlayer.create(this, R.raw.lop);
//        mPlayer1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mPlayer1.stop();
//            }
//        });
//
//        mPlayer2= MediaPlayer.create(this, R.raw.shoot2);
//        mPlayer2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mPlayer2.stop();
//            }
//        });

        draw2d=new Draw2D(this);
        setContentView(draw2d);



        sm=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        if(sm!=null)s=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sv=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                draw2d.xyzSet(event.values[0],event.values[1],event.values[2]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    public void onResume(){
        super.onResume();
        sm.registerListener(sv,s,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause(){
        super.onPause();
        sm.unregisterListener(sv);
    }
}