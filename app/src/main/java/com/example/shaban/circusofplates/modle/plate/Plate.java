package com.example.shaban.circusofplates.modle.plate;

import android.graphics.Bitmap;

import com.example.shaban.circusofplates.utils.GameUtils;

import static java.lang.Thread.interrupted;
import static java.lang.Thread.sleep;

/**
 * Created by shaban on 7/9/2018.
 */

public abstract class Plate{

    protected static int x;
    protected static int y;
    protected static Bitmap bitmap;
    protected boolean isRunning;

    public Plate(int x, int y , Bitmap bitmap) {
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
        isRunning = true;
    }

    public void update() {
    }

    public synchronized static int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public synchronized static int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public synchronized static Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
