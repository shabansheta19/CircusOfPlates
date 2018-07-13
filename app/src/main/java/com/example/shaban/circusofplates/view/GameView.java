package com.example.shaban.circusofplates.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.shaban.circusofplates.R;
import com.example.shaban.circusofplates.control.PlateFactory;
import com.example.shaban.circusofplates.modle.Clown;
import com.example.shaban.circusofplates.modle.plate.Plate;
import com.example.shaban.circusofplates.utils.GameUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PropertyPermission;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class demonstrates the following interactive game basics:
 *
 *   * Manages a rendering thread that draws to a SurfaceView.
 *   * Basic game loop that sleeps to conserve resources.
 *   * Processes user input to update game state.
 *   * Uses clipping as a means of animation.
 *
 * Note that these are basic versions of these techniques.
 * Non-fatal edge cases are not handled.
 * Error handling is minimal. No logging. App assumes and uses a single thread.
 * Additional thread management would otherwise be necessary. See code comments.
 */

public class GameView extends SurfaceView implements Runnable {

    private Context context;
    private int viewWidth;
    private int viewHeight;
    private List<Plate> inSidePlates;
    private List<Plate> catchPlatesLeft;
    private List<Plate> catchPlatesRight;
    private PlateFactory plateFactory;

    private Timer timer;
    private int timerCounter;
    private Thread producerThread;

    //boolean variable to track if the game is playing or not
    volatile boolean playing;

    //the game thread
    private Thread gameThread = null;

    //These objects will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;


    //Class constructor
    public GameView(Context context) {
        super(context);

        this.context = context;
        //inSidePlates = new ArrayList<>();
        inSidePlates = Collections.synchronizedList(new ArrayList<Plate>());
        catchPlatesLeft = new ArrayList<>();
        catchPlatesRight = new ArrayList<>();
        plateFactory = new PlateFactory();

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        GameUtils.setViewWidth(viewWidth);
        GameUtils.setViewHeight(viewHeight);
    }

    @Override
    public void run() {
        while (playing) {
            //to update the frame
            update();

            //to draw the frame
            draw();

            //to control
            control();
        }
    }


    private void update() {
        try {
            for (Plate p : inSidePlates) {
                GameUtils.STATUS status = p.update();
                if (status == GameUtils.STATUS.OUT_SIDE) {
                    inSidePlates.remove(p);
                } else if (status == GameUtils.STATUS.LEFT_CATCH) {
                    catchPlatesLeft.add(p);
                    Clown.getInstance().setY1(-1,p.getPlateHeight());
                    inSidePlates.remove(p);
                } else if (status == GameUtils.STATUS.RIGHT_CATCH) {
                    catchPlatesRight.add(p);
                    Clown.getInstance().setY1(-1,p.getPlateHeight());
                    inSidePlates.remove(p);
                }
            }
        } catch (Exception e) {}
    }

    private void draw() {
        //checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            //locking the canvas
            canvas = surfaceHolder.lockCanvas();
            //drawing a background color for canvas
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_bg);
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(bitmap,0,0,paint);

            GameUtils.setViewWidth(viewWidth);
            GameUtils.setViewHeight(viewHeight);

            //draw clown
            canvas.drawBitmap(Clown.getInstance().getBitmap(),Clown.getInstance().getX(),Clown.getInstance().getY(), paint);

            try {
                for (Plate plate : inSidePlates) {
                    canvas.drawBitmap(plate.getBitmap(), plate.getX(), plate.getY(), paint);
                }
            } catch (Exception e) {}

            for (Plate plate : catchPlatesLeft) {
                canvas.drawBitmap(plate.getBitmap(), plate.getX(), plate.getY(), paint);
            }

            for (Plate plate : catchPlatesRight) {
                canvas.drawBitmap(plate.getBitmap(), plate.getX(), plate.getY(), paint);
            }

            //draw score
            Paint p = new Paint();
            p.setColor(Color.WHITE);
            p.setTextSize(40);
            canvas.drawText(""+timerCounter, 10, 50, p);

            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            gameThread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called by GameActivity.onPause() to stop the thread.
     */
    public void pause() {
        //when the game is paused
        //setting the variable to false
        playing = false;
        try {
            //stopping the thread
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    /**
     * Called by GameActivity.onResume() to start a thread.
     */
    public void resume() {
        //when the game is resumed
        //starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
        startTimer();
        startPlatesProducer();
    }

    /**
     * We cannot get the correct dimensions of views in onCreate because
     * they have not been inflated yet. This method is called every time the
     * size of a view changes, including the first time after it has been
     * inflated.
     *
     * @param w Current width of view.
     * @param h Current height of view.
     * @param oldw Previous width of view.
     * @param oldh Previous height of view.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
        GameUtils.setViewWidth(viewWidth);
        GameUtils.setViewHeight(viewHeight);
    }

    public void startTimer() {
        timerCounter = 100;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timerCounter--;
            }
        },100,1000);
    }

    public void startPlatesProducer() {
        GameUtils.setViewWidth(viewWidth);
        GameUtils.setViewHeight(viewHeight);
        producerThread = new Thread() {

            @Override
            public void run() {
                while (playing) {
                    Plate plate = plateFactory.getPlate(context,new Random().nextInt(3));
                    synchronized(inSidePlates) {
                        inSidePlates.add(plate);
                    }
                    try {
                        sleep(700);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        producerThread.start();
    }

}