package com.example.chinesebaloon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.app.AppOpsManagerCompat;

import java.util.ArrayList;

public class Draw2D extends View  {
    private final int xShift=10;
    private final int columns=15;
    private final int rows=30;
    private final int nextLevel=10;
    private int[]count;
    private Paint mPaint;
    private Screen screen;

    private Cross cross;
    private MyCircle baloon;
    private ArrayList<Hole>holes=new ArrayList<>();

    private ArrayList<Piece>pieces=new ArrayList<>();
    private ArrayList<Cloud>clouds=new ArrayList<>();
    private int score=0;
    private int level=1;
    private int time;
    private final int TIME_CONST=300;
    private double xy=0,xz=0,yz=0;
    private boolean autoShoot=false;
    private int autoX=0;
    private int autoY=0;
    private int autoReload=0;
    private int isReload=10;
    private MediaPlayer mp1,mp2;

    private Context context;

    public Draw2D(Context context){

        super(context);
        mp1= MediaPlayer.create(context, R.raw.lop);
        //mp2= MediaPlayer.create(context, R.raw.shoot3);
        this.context=context;

        mPaint = new Paint();

        screen =new Screen(xShift,columns,rows);
        cross=new Cross(400,600);
        baloon=new MyCircle(400,600,75,100,50);
        time=TIME_CONST;

//        mp1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mp.stop();
//            }
//        });


    }



    String xyStr="";
    public boolean onTouchEvent(MotionEvent event){
        xyStr=""+event.getX()+" "+event.getY();
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                //holes.add(new Hole(cross.getX(),cross.getY(),cross.getR()));
                if(cross.isAuto())
                {
                    autoShoot=true;
                    autoX=(int)event.getX();
                    autoY=(int)event.getY();
                }
                else
                {
                    holes.add(new Hole((int)event.getX(),(int)event.getY(),cross.getR(),context,R.raw.shoot3));
                    //holes.add(new Hole(cross.getX(),cross.getY(),cross.getR(),mp2));
//                    if(mp2.isPlaying()){
//                        mp2.stop();
//                    }
                    //mp2.start();
                    if(holes.get(holes.size()-1).collision(baloon.getX(),baloon.getY(),baloon.getZ()))
                    {
                        if(baloon.isWhole()) {
                            baloon.blow(pieces);
                            mp1.start();
                            score++;
                            time=TIME_CONST;
                            if(score%nextLevel==0)
                            {
                                level++;
                                baloon.setSpeed(baloon.getSpeed()+1);
                                if(level>1)
                                {
                                    cross.setAuto(true);
                                }
                                clouds.add(new Cloud(screen));
                            }

                        }
                        //baloon.randomSet(screen.getX1(),screen.getY1(),screen.getX2(),screen.getY2());
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                autoShoot=false;
                break;
            case MotionEvent.ACTION_MOVE:
                autoX=(int)event.getX();
                autoY=(int)event.getY();

                break;
        }
        return true;
    }

    public void holeRemove(){
        if(holes.size()>0) {
            if (holes.get(0).getTime() == 0) {
                holes.get(0).stopSound();
                holes.remove(0);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        canvas.drawPaint(mPaint);
        mPaint.setAntiAlias(true);

        screen.draw(canvas,mPaint);

        if(time>0) {
            baloon.move(screen.getX1(),screen.getY1(),screen.getX2(),screen.getY2());
            baloon.draw(canvas,mPaint);



            boolean flag=false;
            for(Piece piece:pieces)
            {
                if(piece.getStep()>0) {
                    piece.move();
                    piece.draw(canvas, mPaint);
                }
                else{
                    flag=true;
                }
            }
            if(flag)
            {
                pieces.clear();
                baloon.randomSet(screen.getX1(),screen.getY1(),screen.getX2(),screen.getY2());
                baloon.setWhole(true);
            }

            if(clouds.size()>0)
            {
                for(Cloud cloud:clouds)
                {
                    cloud.move();
                    cloud.draw(canvas,mPaint);
                }
            }


            if(autoShoot)
            {
                if(autoReload==isReload) {
                    holes.add(new Hole(autoX, autoY, cross.getR(),context,R.raw.shoot3));
                    //holes.add(new Hole(cross.getX(),cross.getY(),cross.getR(),mp2));
                    //mp2.start();
                    if(holes.get(holes.size()-1).collision(baloon.getX(),baloon.getY(),baloon.getZ()))
                    {
                        if(baloon.isWhole()) {
                            baloon.blow(pieces);
                            mp1.start();
                            score++;
                            time=TIME_CONST;
                            if(score%nextLevel==0)
                            {
                                level++;
                                baloon.setSpeed(baloon.getSpeed()+1);
                                clouds.add(new Cloud(screen));
                            }

                        }
                        //baloon.randomSet(screen.getX1(),screen.getY1(),screen.getX2(),screen.getY2());
                    }
                    autoReload=0;
                }
                autoReload++;
            }


            holeRemove();
            for(Hole hole:holes)
            {
                hole.draw(canvas,mPaint);


            }




//        mPaint.setColor(Color.BLACK);
//        mPaint.setStrokeWidth(1);
//        mPaint.setTextSize(20);
//        canvas.drawText(""+xy,screen.getLinex()+20,screen.getY1()+20*screen.getScale(),mPaint);
//        canvas.drawText(""+xz,screen.getLinex()+20,screen.getY1()+22*screen.getScale(),mPaint);
//        canvas.drawText(""+yz,screen.getLinex()+20,screen.getY1()+24*screen.getScale(),mPaint);

            cross.move(xy,xz,yz,screen.getX1(),screen.getY1(),screen.getX2(),screen.getY2());
            cross.draw(canvas,mPaint);


            time--;
        }
        else
        {
            mPaint.setColor(Color.RED);
            mPaint.setTextSize(100);
            canvas.drawText("GAME OVER",screen.getX1()+60,(screen.getY1()+screen.getY2())/2,mPaint);
        }

        mPaint.setColor(Color.RED);
        mPaint.setTextSize(20);
        canvas.drawText("Level "+level,screen.getX1()+20,screen.getY2()-20,mPaint);
        canvas.drawText("Score "+score,screen.getX1()+220,screen.getY2()-20,mPaint);
        canvas.drawText("Time Left "+time/20,screen.getX1()+480,screen.getY2()-20,mPaint);

        invalidate();
    }

    public void xyzSet(double xy,double xz,double yz){
        this.xy=xy;
        this.xz=xz;
        this.yz=yz;
    }

}
