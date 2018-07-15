package com.example.shaban.circusofplates.modle.plate;

import android.graphics.Bitmap;

import com.example.shaban.circusofplates.utils.GameUtils;

import static java.lang.Thread.sleep;

/**
 * Created by shaban on 7/9/2018.
 */

public class TopPlate extends Plate {

    public TopPlate(int x, int y, Bitmap bitmap , int id) {
        super(x, y, bitmap,id);
    }

    @Override
    public GameUtils.STATUS update(int yLeft , int yRight) {
        y += 10;
        check(yLeft,yRight);
        return status;
    }
}
