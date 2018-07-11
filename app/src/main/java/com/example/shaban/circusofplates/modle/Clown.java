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
    private static Clown instance ;
    private int x;
    private int y;
    private int width;
    private int height;
    private Bitmap bitmap;
    private Context context;

    private Clown() {
    }

    public synchronized static Clown getInstance() {
        if (instance == null)
            instance = new Clown();
        return instance;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        y = GameUtils.getViewHeight() - height;
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void init(Context context) {
        this.context = context;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.clown);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        y = GameUtils.getViewHeight() - height;
    }
}
