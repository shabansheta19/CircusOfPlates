package com.example.shaban.circusofplates.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.shaban.circusofplates.control.PlateFactory;
import com.example.shaban.circusofplates.modle.Clown;
import com.example.shaban.circusofplates.modle.plate.Plate;
import com.example.shaban.circusofplates.utils.GameUtils;
import java.util.List;
import java.util.Random;

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

public class GameView extends View {

    private boolean gameRunning;
    private Context context;
    private Paint paint;
    private int viewWidth;
    private int viewHeight;
    private PlateFactory plateFactory;

    public GameView(Context context) {
        super(context);
        this.context = context;
        plateFactory = new PlateFactory();
        invalidate();
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

    /**
     * Called by MainActivity.onPause() to stop the thread.
     */
    public void pause() {
        gameRunning = false;
    }

    /**
     * Called by MainActivity.onResume() to start a thread.
     */
    public void resume() {
        gameRunning = true;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        GameUtils.setViewWidth(viewWidth);
        GameUtils.setViewHeight(viewHeight);
        //draw clown
        canvas.drawBitmap(Clown.getInstance().getBitmap(),Clown.getInstance().getX(),Clown.getInstance().getY(), paint);

        //draw plates
        //List<Plate> plates = GameUtils.getPlates();
        //Log.d("plates length:  ","  "+plates.size());
        try {
            for (int i = 0 ; i < GameUtils.getPlates().size() ; i++) {
                canvas.drawBitmap(GameUtils.getPlates().get(i).getBitmap(), GameUtils.getPlates().get(i).getX(), GameUtils.getPlates().get(i).getY(), paint);
            }
        } catch (Exception e) {

        }

        //draw score
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        canvas.drawText(""+GameUtils.getTimerCounter(), 10, 50, paint);
        if(gameRunning) {
            invalidate();
        }
    }

}