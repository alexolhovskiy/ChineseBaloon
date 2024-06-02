package com.example.chinesebaloon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Cross {
    private final int autoR=50;
    private final int singleR=20;
    private int X;
    private int Y;
    private final int R=50;
    private int r;
    private int maxSpeed;
    private boolean auto;

    public Cross(int x,int y){
        auto=false;
        maxSpeed=50;
        X=x;
        Y=y;
        r=singleR;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getR() {
        if(auto)
        {
            return autoR;
        }
        return singleR;
    }

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
        if(this.auto)
        {
            r=autoR;
        }
        else
        {
            r=singleR;
        }
    }

    public void move(double xy, double xz, double yz, int borMinX, int borMinY, int borMaxX, int borMaxY){

        X-=xy*maxSpeed/9.8;
        Y+=xz*maxSpeed/9.8;
        if(X<borMinX)
        {
            X=borMinX;
        }
        if(X>borMaxX)
        {
            X=borMaxX;
        }
        if(Y<borMinY)
        {
            Y=borMinY;
        }
        if(Y>borMaxY)
        {
            Y=borMaxY;
        }

    }

    public void draw(Canvas canvas, Paint paint){
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(4);
        canvas.drawLine(X-(R+r),Y,X-r,Y,paint);
        canvas.drawLine(X+r,Y,X+R+r,Y,paint);
        canvas.drawLine(X,Y-(R+r),X,Y-r,paint);
        canvas.drawLine(X,Y+r,X,Y+r+R,paint);
    }
}
