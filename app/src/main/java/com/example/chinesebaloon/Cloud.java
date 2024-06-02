package com.example.chinesebaloon;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

public class Cloud {
    private int x, y, maxR = 100, fluff_index = 6;
    ArrayList<Fluff> fluffs = new ArrayList<>();
    private boolean left = true;
    private Random rand;
    private Screen screen;

    public Cloud(Screen screen) {
        rand = new Random();
        this.screen = screen;
        x = this.screen.getX1() + rand.nextInt(this.screen.getW()-maxR*(fluff_index-1));//!
        y = (this.screen.getY1() + maxR) + rand.nextInt(this.screen.getH() - 2 * maxR);
        setFluffs();
    }

    public void setFluffs() {
        int tempR = 0, tempX = 0;
        if (rand.nextInt(100) > 50) {
            left = true;
            for (int i = 0; i < fluff_index; i++) {
                if (i == 0) {
                    tempR = (int) (Math.sin(i * Math.PI / fluff_index) * maxR);
                    fluffs.add(new Fluff((screen.getX1() - maxR * 2 * fluff_index), y, tempR));
                } else {
                    tempR = (int) (Math.sin(i * Math.PI / fluff_index) * maxR);
                    if (tempR >= fluffs.get(i - 1).getR()) {
                        tempX = fluffs.get(i - 1).getX() + tempR;
                    } else {
                        tempX = fluffs.get(i - 1).getX() + fluffs.get(i - 1).getR();
                    }
                    fluffs.add(new Fluff(tempX, y, tempR));
                }
            }
        } else {
            left = false;
            for (int i = 0; i < fluff_index; i++) {
                if (i == 0) {
                    tempR = (int) (Math.sin(i * Math.PI / fluff_index) * maxR);
                    fluffs.add(new Fluff((screen.getX2() + maxR * 2), y, tempR));
                } else {
                    tempR = (int) (Math.sin(i * Math.PI / fluff_index) * maxR);
                    if (tempR >= fluffs.get(i - 1).getR()) {
                        tempX = fluffs.get(i - 1).getX() + tempR;
                    } else {
                        tempX = fluffs.get(i - 1).getX() + fluffs.get(i - 1).getR();
                    }
                    fluffs.add(new Fluff(tempX, y, tempR));
                }
            }
        }
    }

    private int step = 10;

    public void move() {
        if (left) {
            if (fluffs.get(0).getX() < x) {
                for (Fluff fluff : fluffs) {
                    fluff.setX(fluff.getX() + step);
                }
            }
        } else {
            if (fluffs.get(0).getX() > x) {
                for (Fluff fluff : fluffs) {
                    fluff.setX(fluff.getX() - step);
                }
            }
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        for (Fluff fluff : fluffs) {
            fluff.draw(canvas, paint);
        }
    }

}