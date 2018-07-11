package com.example.shaban.circusofplates.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shaban on 7/9/2018.
 */

public abstract class GameUtils {

    private static int viewWidth;
    private static int viewHeight;
    private static int timerCounter;
    private static Timer timer;
    public static enum STATUS {IN_SIDE , OUT_SIDE , CATCH};


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

    public synchronized static int getTimerCounter() {
        return timerCounter;
    }

    public static void startTimer() {
        timerCounter = 100;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timerCounter--;
            }
        },100,1000);
    }
}
