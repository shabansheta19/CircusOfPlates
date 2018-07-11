package com.example.shaban.circusofplates.control;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.shaban.circusofplates.modle.plate.LeftPlate;
import com.example.shaban.circusofplates.modle.plate.Plate;
import com.example.shaban.circusofplates.modle.plate.RightPlate;
import com.example.shaban.circusofplates.modle.plate.TopPlate;
import com.example.shaban.circusofplates.utils.GameUtils;
import com.example.shaban.circusofplates.utils.PlateUtils;

import java.util.Random;

/**
 * Created by shaban on 7/10/2018.
 */

public class PlateFactory {

    /**
     * return new plate based on plate id.
     * @param plateId (0 -> left plate) (1 -> top plate) (2 -> right plate)
     * @return new plate.
     */
    public Plate getPlate(Context context , int plateId) {
        Plate plate = null;
        Bitmap bitmap = PlateUtils.getPlateBitmap(context);
        int x,y;
        if (plateId == 0) {
            //left plate
            x = 0;
            y = new Random().nextInt(100);
            plate = new LeftPlate(x,y,bitmap);
        } else if (plateId == 1) {
            //top plate
            x = new Random().nextInt(GameUtils.getViewWidth() - 200) + 100;
            y = 0;
            plate = new TopPlate(x,y,bitmap);
        } else {
            //right plate
            x = GameUtils.getViewWidth() - 25;
            y = new Random().nextInt(100);
            plate = new RightPlate(x,y,bitmap);
        }
        return plate;
    }
}
