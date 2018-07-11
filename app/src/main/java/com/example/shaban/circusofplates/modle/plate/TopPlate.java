package com.example.shaban.circusofplates.modle.plate;

import android.graphics.Bitmap;

import com.example.shaban.circusofplates.utils.GameUtils;

import static java.lang.Thread.sleep;

/**
 * Created by shaban on 7/9/2018.
 */

public class TopPlate extends Plate {

    public TopPlate(int x, int y, Bitmap bitmap) {
        super(x, y, bitmap);
    }

    @Override
    public void update() {
        y += 10;
        if (x < 0 || x > GameUtils.getViewWidth() || y > (GameUtils.getViewHeight() - 100)) {
            isRunning = false;
            GameUtils.getPlates().remove(this);
        }
    }
}