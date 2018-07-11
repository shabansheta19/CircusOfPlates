package com.example.shaban.circusofplates.modle.plate;

import android.graphics.Bitmap;

import com.example.shaban.circusofplates.utils.GameUtils;

/**
 * Created by shaban on 7/9/2018.
 */

public abstract class Plate{

    protected int x;
    protected int y;
    protected Bitmap bitmap;
    protected GameUtils.STATUS status;

    public Plate(int x, int y , Bitmap bitmap) {
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
        status = GameUtils.STATUS.IN_SIDE;
    }

    public GameUtils.STATUS update() {
        return status;
    }

    public void check() {
        if (x < 0 || x > GameUtils.getViewWidth() || y > (GameUtils.getViewHeight() - 100)) {
            status = GameUtils.STATUS.OUT_SIDE;
        } else {

        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
