package com.example.shaban.circusofplates.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.shaban.circusofplates.modle.Clown;
import com.example.shaban.circusofplates.modle.plate.Plate;
import com.example.shaban.circusofplates.utils.GameUtils;

import java.util.List;

/**
 * Example activity that contains a view that reads accelerometer sensor input and
 * sets the clown position based on the changes.
 */
public class GameActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private GameView gameView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        gameView = new GameView(this);

        //Set our content to a view, not like the traditional setting to a layout
        setContentView(gameView);

        GameUtils.setViewWidth(gameView.getWidth());
        GameUtils.setViewHeight(gameView.getHeight());

        Clown.getInstance().init(this);

        //start game counter
        GameUtils.startTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) { }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            int newX = Clown.getInstance().getX() - (int) event.values[0];
            //Make sure we do not draw outside the bounds of the view.
            //So the max values we can draw to are the bounds + the size of clown.
            if (newX < 0) {
                newX = 0;
            }
            if (newX > GameUtils.getViewWidth() - Clown.getInstance().getWidth()) {
                newX = GameUtils.getViewWidth() - Clown.getInstance().getWidth();
            }
            Clown.getInstance().setX(newX);
        }
    }
}
