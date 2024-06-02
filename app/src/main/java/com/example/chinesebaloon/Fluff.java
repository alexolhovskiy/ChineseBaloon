package com.example.chinesebaloon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Fluff {
    private int R,x,y;
    public Fluff(int x,int y,int R){
        this.R=R;
        this.x=x;
        this.y=y;
    }

    public int getR() {
        return R;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void draw(Canvas canvas, Paint paint){
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x,y,R,paint);
    }
}
