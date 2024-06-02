package com.example.chinesebaloon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;

public class Piece {
    private ArrayList<Point> arr=new ArrayList<>();
    double vx,vy,svx,svy,step;
    private Path path;
    public Piece(ArrayList<Point> arr)
    {
        this.arr=arr;
        this.vx=this.arr.get(2).getX()-this.arr.get(0).getX();
        this.vy=this.arr.get(2).getY()-this.arr.get(0).getY();
        this.step=Math.sqrt(vx*vx+vy*vy);//this.arr.get(1).getY();
        this.svx=this.vx/this.step;
        this.svy=this.vy/this.step;
    }

    public void move()
    {
        this.step-=8.0;
        this.vx=this.svx*this.step;
        this.vy=this.svy*this.step;
        this.arr.get(0).setX(this.arr.get(0).getX()+this.vx);
        this.arr.get(0).setY(this.arr.get(0).getY()+this.vy);
//        this.arr.get(2).setX(this.arr.get(2).getX()+this.vx);
//        this.arr.get(2).setY(this.arr.get(2).getY()+this.vy);

        path=new Path();
        path.moveTo((float)arr.get(0).getX(),(float)arr.get(0).getY());
        path.lineTo((float)(arr.get(0).getX()+Math.cos(this.arr.get(1).getX())*step),
                (float)(arr.get(0).getY()+Math.sin(this.arr.get(1).getX())*step));
        path.lineTo((float)(arr.get(0).getX()+Math.cos(this.arr.get(3).getX())*step),
                (float)(arr.get(0).getY()+Math.sin(this.arr.get(3).getX())*step));
    }

    public double getStep() {
        return step;
    }

    public double getSvx() {
        return svx;
    }

    public double getSvy() {
        return svy;
    }

    public boolean equals(Piece o){
        return this.svx==o.getSvx()&&this.svy==o.getSvy();
    }

    public void draw(Canvas canvas, Paint paint)
    {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);//ctx.fillStyle="rgba(0,255,0,"+this.a+")";
        canvas.drawPath(path, paint);
    }
}
