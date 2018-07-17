package com.example.shaban.circusofplates.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import com.example.shaban.circusofplates.R;
import com.example.shaban.circusofplates.control.PlateFactory;
import com.example.shaban.circusofplates.modle.Clown;
import com.example.shaban.circusofplates.modle.plate.Plate;
import com.example.shaban.circusofplates.utils.GameUtils;
import java.util.ArrayList;
import java.util.List;
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

    private Activity context;
    private int viewWidth;
    private int viewHeight;
    private List<Plate> inSidePlates;
    private List<Plate> catchPlatesLeft;
    private List<Plate> catchPlatesRight;
    private PlateFactory plateFactory;
    private long startTime;
    private Timer timer;
    private int timerCounter;
    private int score = 0;
    //boolean variable to track if the game is playing or not
    volatile boolean playing;
    //the game thread
    private Thread gameThread = null;
    //These objects will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    //pause and resume bitmap
    private Bitmap pauseBtn;
    private Bitmap resumeBtn;

    //Class constructor
    public GameView(Activity context) {
        super(context);
        pauseBtn = BitmapFactory.decodeResource(getResources(),R.drawable.pause_btn);
        resumeBtn = BitmapFactory.decodeResource(getResources(),R.drawable.resume_btn);
        this.context = context;
        inSidePlates = new ArrayList<>();
        catchPlatesLeft = new ArrayList<>();
        catchPlatesRight = new ArrayList<>();
        plateFactory = new PlateFactory();
        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
        GameUtils.setViewWidth(viewWidth);
        GameUtils.setViewHeight(viewHeight);
        timerCounter = 100;
    }

    @Override
    public void run() {
        Log.d("inSidePlates = ",inSidePlates.size()+"");
        while (playing) {
            //to draw the frame
            draw();
            //to update the frame
            update();
            //to control
            //control();
        }
    }

    private void update() {
        while (System.currentTimeMillis() - startTime >= 700) {
            Plate plate = plateFactory.getPlate(context, new Random().nextInt(3));
            inSidePlates.add(plate);
            startTime = System.currentTimeMillis();
        }
        for (int i = 0 ; i < inSidePlates.size() ; i++) {
            Plate p = inSidePlates.get(i);
            GameUtils.STATUS status = p.update(p.getPlateHeight()*catchPlatesLeft.size(),p.getPlateHeight()*catchPlatesRight.size());
            if (status == GameUtils.STATUS.OUT_SIDE) {
                inSidePlates.remove(p);
            } else if (status == GameUtils.STATUS.LEFT_CATCH) {
                if (catchPlatesLeft.size() >= 2 && (p.getId() == catchPlatesLeft.get(catchPlatesLeft.size()-1).getId()) && (p.getId() == catchPlatesLeft.get(catchPlatesLeft.size()-2).getId())) {
                    catchPlatesLeft.remove(catchPlatesLeft.size()-1);
                    catchPlatesLeft.remove(catchPlatesLeft.size()-1);
                    score += 3;
                } else {
                    catchPlatesLeft.add(p);
                }
                inSidePlates.remove(p);
            } else if (status == GameUtils.STATUS.RIGHT_CATCH) {
                if (catchPlatesRight.size() >= 2 && (p.getId() == catchPlatesRight.get(catchPlatesRight.size()-1).getId()) && (p.getId() == catchPlatesRight.get(catchPlatesRight.size()-2).getId())) {
                    catchPlatesRight.remove(catchPlatesRight.size()-1);
                    catchPlatesRight.remove(catchPlatesRight.size()-1);
                    score += 3;
                } else {
                    catchPlatesRight.add(p);
                }
                inSidePlates.remove(p);
            }
        }
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
            int clownX = Clown.getInstance().getX();
            canvas.drawBitmap(Clown.getInstance().getBitmap(),clownX,Clown.getInstance().getY(), paint);

            Plate plate;

            for (int i = 0 ; i < inSidePlates.size() ; i++) {
                plate = inSidePlates.get(i);
                canvas.drawBitmap(plate.getBitmap(), plate.getX(), plate.getY(), paint);
            }

            for (int i = 0 ; i < catchPlatesLeft.size() ; i++) {
                plate = catchPlatesLeft.get(i);
                canvas.drawBitmap(plate.getBitmap(), clownX+3, Clown.getInstance().getY() - (i+1)*plate.getPlateHeight(), paint);
            }

            for (int i = 0 ; i < catchPlatesRight.size() ; i++) {
                plate = catchPlatesRight.get(i);
                canvas.drawBitmap(plate.getBitmap(), clownX+Clown.getInstance().getWidth()-plate.getPlateWidth()+3,Clown.getInstance().getY() - (i+1)*plate.getPlateHeight(), paint);
            }

            //draw time
            Paint p = new Paint();
            p.setColor(Color.WHITE);
            p.setTextSize(40);
            canvas.drawText(""+timerCounter, 10, 50, p);

            //draw score
            canvas.drawText("score: "+score, 10, 100, p);

            //draw pause/resume button
            if(playing) {
                canvas.drawBitmap(pauseBtn,viewWidth - 50,50,paint);
            } else {
                canvas.drawBitmap(resumeBtn,viewWidth - 50,50,paint);
            }

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
        Log.d("inSidePlates = ",inSidePlates.size()+"");
        startTime = System.currentTimeMillis();
        startTimer();
        gameThread = new Thread(this);
        Log.d("inSidePlates = ",inSidePlates.size()+"");
        gameThread.start();
        Log.d("inSidePlates = ",inSidePlates.size()+"");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN: {
                int posX = (int)event.getX();
                int posY = (int)event.getY();
                if((posY >= 50 || posY <= pauseBtn.getHeight()+50) && (posX >= viewWidth-50 || posX <= viewWidth+pauseBtn.getWidth()-50)) {
                    if (playing) {
                        pause();
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                openDialog();
                            }
                        });
                    } else {
                        resume();
                    }
                }
            }
        }
        return true;
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
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timerCounter--;
            }
        },1000,1000);
    }

    public List<Plate> getCatchPlatesLeft() {
        return catchPlatesLeft;
    }

    public void setCatchPlatesLeft(List<Plate> catchPlatesLeft) {
        this.catchPlatesLeft = catchPlatesLeft;
    }

    public List<Plate> getInSidePlates() {
        return inSidePlates;
    }

    public void setInSidePlates(List<Plate> inSidePlates) {
        this.inSidePlates = inSidePlates;
    }

    public List<Plate> getCatchPlatesRight() {
        return catchPlatesRight;
    }

    public void setCatchPlatesRight(List<Plate> catchPlatesRight) {
        this.catchPlatesRight = catchPlatesRight;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTimerCounter() {
        return timerCounter;
    }

    public void setTimerCounter(int timerCounter) {
        this.timerCounter = timerCounter;
    }

    public void openDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pause_dialog_layout);
        Button resumeBtn = (Button)dialog.findViewById(R.id.resume_dialog_button);
        Button saveBtn = (Button)dialog.findViewById(R.id.save_dialog_button);
        Button exitBtn = (Button)dialog.findViewById(R.id.exit_dialog_button);
        resumeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resume();
                dialog.dismiss();
            }
        });

        saveBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //storage utils.
                GameActivity.saveGame();
                Toast.makeText(context,"game saved",Toast.LENGTH_LONG).show();
                //context.startActivity(new Intent(context,MainActivity.class));
                //dialog.dismiss();
            }
        });

        exitBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //exit the game.
                dialog.dismiss();
                context.startActivity(new Intent(context,MainActivity.class));
            }
        });
        dialog.show();
    }
}