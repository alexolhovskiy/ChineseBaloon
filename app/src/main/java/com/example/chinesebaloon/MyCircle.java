package com.example.chinesebaloon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.Random;

public class MyCircle {
    private int x,y,speed,starNum=12;
    private double z,R,r;
    private boolean xFlag,yFlag,zFlag;
    private boolean whole;
    private ArrayList<Point>arr=new ArrayList<>();
    private Path path;
    private Random rand;
    private int startLeft,startTop,endLeft,endTop;

    private int time=255;
    public MyCircle(int y,int x,double z,double R,double r){
        this.x=x;
        this.y=y;
        this.z=z;
        this.R=R;
        this.r=r;
        xFlag=true;
        yFlag=false;
        zFlag=true;
        speed=2;
        whole=true;
        rand=new Random();
    }

    public void randomSet(int borMinX,int borMinY,int borMaxX,int borMaxY){
        x=borMinX+rand.nextInt(borMaxX-borMinX);
        y=borMinY+rand.nextInt(borMaxY-borMinY);
        z=r+rand.nextDouble()*(R-r);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void move(int borMinX, int borMinY, int borMaxX, int borMaxY){
        if(whole) {
            if (xFlag) {
                if ((x + z) < borMaxX) {
                    x += speed;
                } else {
                    xFlag = false;
                }
            } else {
                if ((x - z) > borMinX) {
                    x -= speed;
                } else {
                    xFlag = true;
                }
            }
            if (yFlag) {
                if ((y + z) < borMaxY) {
                    y += speed;
                } else {
                    yFlag = false;
                }
            } else {
                if ((y - z) > borMinY) {
                    y -= speed;
                } else {
                    yFlag = true;
                }
            }
            double zStep = (R - r) / ((double) (borMaxX - borMinX) / (double) speed);
            if (zFlag) {
                if (z < R) {
                    z += zStep;
                } else {
                    zFlag = false;
                }
            } else {
                if (z > r) {
                    z -= zStep;
                } else {
                    zFlag = true;
                }
            }
        }
        else
        {
            for(int i=0;i<starNum;i++)
            {
                this.arr.get(i).setY(this.arr.get(i).getY()+3);
            }
            starMove();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void blow(ArrayList<Piece>pieces)
    {
        whole=false;
        double step=Math.PI/6;

        for(int i=0;i<24;i+=2)
        {
            ArrayList<Point>list=new ArrayList<>();
            list.add(new Point(this.x,this.y));
            list.add(new Point((step*i),this.z));
            list.add(new Point(this.x+Math.cos(step*(i+1))*this.z,this.y+Math.sin(step*(i+1))*this.z));
            list.add(new Point((step*(i+2)),this.z));
            pieces.add(new Piece(list));
        }
        double r=z;
        double a=2*Math.PI/starNum;
        for(int i=0;i<starNum;i++)
        {
            if(i%2==0)
            {
                this.arr.add(new Point(a*i,r));
            }
            else
            {
                this.arr.add(new Point(a*i,r+Math.random()*2*r));
            }
        }

        starMove();
//        var audio=new Audio();
//        audio.src="lop.mp3";
//        audio.autoplay=true;
    }

    public void starMove(){
        path=new Path();
        path.moveTo((float)(this.x+Math.cos(this.arr.get(0).getX())*this.arr.get(0).getY()),
                (float)(this.y+Math.sin(this.arr.get(0).getX())*this.arr.get(0).getY()));
        for(int i=1;i<starNum;i++)
        {
            path.lineTo((float)(this.x+Math.cos(this.arr.get(i).getX())*this.arr.get(i).getY()),
                    (float)(this.y+Math.sin(this.arr.get(i).getX())*this.arr.get(i).getY()));
        }
    }

    public boolean isWhole() {
        return whole;
    }

    public void setWhole(boolean whole) {
        this.whole = whole;
    }

    public void draw(Canvas canvas, Paint paint){
        if(whole) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            canvas.drawCircle(x, y, (int) z, paint);
        }
        else
        {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.argb(time,0,255,0));//ctx.fillStyle="rgba(0,255,0,"+this.a+")";
            canvas.drawPath(path,paint);
            if(time>0)
            {
                time-=50;
            }
            else
            {
                //x=rand.nextInt()
            }
        }
    }
}
