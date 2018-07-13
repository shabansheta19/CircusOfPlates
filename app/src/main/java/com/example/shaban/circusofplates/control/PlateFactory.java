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
    Random  random;

    /**
     * constructor.
     */
    public PlateFactory() {
        random = new Random();
    }

    /**
     * return new plate based on plate id.
     * @param plateId (0 -> left plate) (1 -> top plate) (2 -> right plate)
     * @return new plate.
     */
    public Plate getPlate(Context context , int plateId) {
        Plate plate = null;
        Bitmap bitmap = PlateUtils.getPlateBitmap(context);
        int y = -10;
        int x = random.nextInt(720);
        //int x = random.nextInt(GameUtils.getViewWidth());
        if (plateId == 0) {
            //left plate
            plate = new LeftPlate(x,y,bitmap);
        } else if (plateId == 1) {
            //top plate
            plate = new TopPlate(x,y,bitmap);
        } else {
            //right plate
            plate = new RightPlate(x,y,bitmap);
        }
        return plate;
    }
}
