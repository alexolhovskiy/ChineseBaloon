package com.example.chinesebaloon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.net.Uri;

import java.util.ArrayList;

public class Hole {
    private static int count;
    static{
        count=0;
    }
    private int time=200;
    private final int num=7;
    private final int radius=15;
    private int x,y;
    private ArrayList<Point>arr=new ArrayList<>();
    private Path path;
    private MediaPlayer mp2;
    public Hole(int x, int y, int R, Context context, int file){
        this.x=x;
        this.y=y;
        mp2= MediaPlayer.create(context, file);
        double a,r,audio;

        a=Math.random()*Math.PI*2;
        r=Math.random()*R;
        this.x+=Math.cos(a)*r;
        this.y+=Math.sin(a)*r;

        r=25;
        a=2*Math.PI/this.num;
        for(int i=0;i<this.num;i++)
        {
            if(i%2==0)
            {
                this.arr.add(new Point(this.x+Math.cos(a*i)*r,this.y+Math.sin(a*i)*r));
            }
            else
            {
                double temp=Math.random()*r;
                this.arr.add(new Point(this.x+Math.cos(a*i)*(r+temp),this.y+Math.sin(a*i)*(r+temp)));
            }
        }

        path=new Path();
        path.moveTo((float)arr.get(0).getX(),(float)arr.get(0).getY());
        for(int i=1;i<this.num;i++)
        {
            path.lineTo((float)arr.get(i).getX(),(float)arr.get(i).getY());
        }

        count++;
//        if(!(count%100))
//        {
//            is_ufo=true;
//            ufo.Set();
//        }
//        audio=new Audio();
//        audio.src="shoot2.mp3";
//        audio.autoplay=true;

        mp2.start();

    }

    public boolean collision(double x,double y,double z)
    {
        if(Math.sqrt(Math.pow((this.x-x),2)+Math.pow((this.y-y),2))<z)
        {
            return true;
        }
        return false;
    }

    public int getTime() {
        return time;
    }

    public void stopSound(){
        mp2.stop();
    }

    public void draw(Canvas canvas, Paint paint){


        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        //path.close();
        canvas.drawPath(path,paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(x,y,radius,paint);
        this.time--;
    }
}
