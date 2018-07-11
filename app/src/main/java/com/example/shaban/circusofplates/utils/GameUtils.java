package com.example.shaban.circusofplates.utils;

import android.content.Context;

import com.example.shaban.circusofplates.control.PlateFactory;
import com.example.shaban.circusofplates.modle.plate.Plate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shaban on 7/9/2018.
 */

public abstract class GameUtils {

    private static GameUtils instance;
    private static int viewWidth;
    private static int viewHeight;
    private static List<Plate> plates = new ArrayList<>();
    private static int timerCounter;
    private static Timer timer;
    private static Thread thread;
    private static PlateFactory plateFactory = new PlateFactory();


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

    public synchronized static List<Plate> getPlates() {
        if (plates == null)
            plates = new ArrayList<>();
        return plates;
    }

    public void setPlates(List<Plate> plates) {
        this.plates = plates;
    }

    public synchronized static int getTimerCounter() {
        return timerCounter;
    }

    public static void startPlatesProducer(final Context context) {
        thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    Plate plate = plateFactory.getPlate(context,new Random().nextInt(3));
                    plates.add(plate);
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
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
