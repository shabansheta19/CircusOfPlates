package com.example.shaban.circusofplates.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shaban on 7/9/2018.
 */

public abstract class GameUtils {

    private static int viewWidth;
    private static int viewHeight;

    public static enum STATUS {IN_SIDE , OUT_SIDE , LEFT_CATCH , RIGHT_CATCH};


    public static int getViewWidth() {
        return viewWidth;
    }

    public static void setViewWidth(int viewW) {
        viewWidth = viewW;
    }

    public static int getViewHeight() {
        return viewHeight;
    }

    public static void setViewHeight(int viewH) {
        viewHeight = viewH;
    }

}
