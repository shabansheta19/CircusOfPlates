package com.example.shaban.circusofplates.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.shaban.circusofplates.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by shaban on 7/9/2018.
 */

public abstract class PlateUtils {
    /********************** plates images ***********************/
    //private final static int BLACK_PLATE = R.drawable.black_plate;
    private final static int RED_PLATE = R.drawable.red_plate;
    //private final static int CYAN_PLATE = R.drawable.cyan_plate;
    private final static int BLUE_PLATE = R.drawable.blue_plate;
    //private final static int DARK_RED_PLATE = R.drawable.darkred_plate;
    //private final static int GOLD_PLATE = R.drawable.gold_plate;
    private final static int GREEN_PLATE = R.drawable.green_plate;
    //private final static int ORANGE_PLATE = R.drawable.orange_plate;
    //private final static int PINK_PLATE = R.drawable.pink_plate;
    //private final static int PURPLE_PLATE = R.drawable.purple_plate;
    //private final static int YELLOW_PLATE = R.drawable.yellow_plate;
    /***************************************************************/

    public static int  idx;

    /***************array contains the plates images id*************/
    public final static int[] platesDrawables = {RED_PLATE,BLUE_PLATE,GREEN_PLATE};
    /***************************************************************/

    /***************array contains the plates images id*************
    private final static int[] platesDrawables = {BLACK_PLATE,RED_PLATE,CYAN_PLATE
            ,BLUE_PLATE,DARK_RED_PLATE,GOLD_PLATE
            ,GREEN_PLATE,ORANGE_PLATE,PINK_PLATE
            ,PURPLE_PLATE,YELLOW_PLATE};
    /***************************************************************/

    public static Bitmap getPlateBitmap(Context context) {
        idx = new Random().nextInt(3);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), platesDrawables[idx]);
        return bitmap;
    }
}
