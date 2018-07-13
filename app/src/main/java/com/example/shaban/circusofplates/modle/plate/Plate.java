package com.example.shaban.circusofplates.modle.plate;

import android.graphics.Bitmap;

import com.example.shaban.circusofplates.modle.Clown;
import com.example.shaban.circusofplates.utils.GameUtils;

/**
 * Created by shaban on 7/9/2018.
 */

public abstract class Plate{

    protected int x;
    protected int y;
    protected int plateWidth;
    protected int plateHeight;
    protected Bitmap bitmap;
    protected GameUtils.STATUS status;

    public Plate(int x, int y , Bitmap bitmap) {
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
        plateHeight = bitmap.getHeight();
        plateWidth = bitmap.getWidth();
        status = GameUtils.STATUS.IN_SIDE;
    }

    public GameUtils.STATUS update() {
        return status;
    }

    public void check() {
        if (x < 0 || x > GameUtils.getViewWidth() || y > GameUtils.getViewHeight()) {
            status = GameUtils.STATUS.OUT_SIDE;
        } else {
            int x1 = Clown.getInstance().getX();
            int x2 = x1 + Clown.getInstance().getWidth() - 10;
            int y1 = Clown.getInstance().getyLeft();
            int y2 = Clown.getInstance().getyRight();
            if(x >= x1-20 && x <= x1+20 && y >= y1+17 && y <= y1+23) {
                status = GameUtils.STATUS.LEFT_CATCH;
                x = x1;
                y = y1 - plateHeight;
            } else if(x >= x2-20 && x <= x2+20 && y >= y2+17 && y <= y2+23) {
                status = GameUtils.STATUS.RIGHT_CATCH;
                x = x2;
                y = y2 - plateHeight;
            }
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

    public int getPlateWidth() {
        return plateWidth;
    }

    public int getPlateHeight() {
        return plateHeight;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
