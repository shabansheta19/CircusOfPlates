package com.example.shaban.circusofplates.modle.plate;

import android.graphics.Bitmap;

import com.example.shaban.circusofplates.utils.GameUtils;

/**
 * Created by shaban on 7/9/2018.
 */

public class LeftPlate extends Plate {

    public LeftPlate(int x, int y, Bitmap bitmap) {
        super(x, y, bitmap);
    }

    @Override
    public GameUtils.STATUS update() {
        x += 3;
        y += 10;
        check();
        return status;
    }
}
