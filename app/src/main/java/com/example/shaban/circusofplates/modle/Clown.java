package com.example.shaban.circusofplates.modle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.shaban.circusofplates.R;
import com.example.shaban.circusofplates.utils.GameUtils;

/**
 * Created by shaban on 7/9/2018.
 */

public class Clown {
    private static Clown instance ; //the only object of clown class.
    private int x; //position of clown according to the x axis.
    private int y; //position of clown according to the x axis.
    private int width; //the width of clown.
    private int height; //the height of clown.
    private Bitmap bitmap; //the bitmap oof clown image.

    /**
     * private constructor to create singleton class.
     */
    private Clown() {
    }

    /**
     * @return the only object of clown class.
     */
    public synchronized static Clown getInstance() {
        if (instance == null)
            instance = new Clown();
        return instance;
    }

    /**
     * @return the position of clown according to the x axis.
     */
    public int getX() {
        return x;
    }

    /**
     * change the position of clown according to the x axis.
     * @param x the new position of clown according to the x axis.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the position of clown according to the y axis.
     */
    public int getY() {
        y = GameUtils.getViewHeight() - height;
        return y;
    }

    /**
     * @return the width of the clown.
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height of the clown.
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the bitmap of the clown.
     */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * set the clown bitmap, width, height and theee y position.
     * @param context to get the resources.
     */
    public void init(Context context) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.clown);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        y = GameUtils.getViewHeight() - height;
    }

}
