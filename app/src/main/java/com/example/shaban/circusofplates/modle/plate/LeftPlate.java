package com.example.shaban.circusofplates.modle.plate;

import android.graphics.Bitmap;

import com.example.shaban.circusofplates.utils.GameUtils;

/**
 * Created by shaban on 7/9/2018.
 */

public class LeftPlate extends Plate {

    public LeftPlate(int x, int y, Bitmap bitmap , int id) {
        super(x, y, bitmap,id);
    }

    @Override
    public GameUtils.STATUS update(int yLeft , int yRight) {
        x += 3;
        y += 10;
        check(yLeft,yRight);
        return status;
    }
}
