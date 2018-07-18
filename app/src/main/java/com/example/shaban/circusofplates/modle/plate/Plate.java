package com.example.shaban.circusofplates.modle.plate;

import android.graphics.Bitmap;

import com.example.shaban.circusofplates.modle.Clown;
import com.example.shaban.circusofplates.utils.GameUtils;

/**
 * Created by shaban on 7/9/2018.
 */

public abstract class Plate{

    protected int id;  //detect the color of plate(each color has unique id)
    protected int x; //position of plate according to the x axis.
    protected int y; //position of plate according to the y axis.
    protected int plateWidth; //the width of plate.
    protected int plateHeight; //the height of plate.
    protected Bitmap bitmap; //the bitmap of plate image.
    protected GameUtils.STATUS status; //the status of plate (inside the game view bounds, outside or clown catch it).

    /**
     * constructor of plate class.
     * @param x initial position of plate according to the x axis.
     * @param y initial position of plate according to the y axis.
     * @param bitmap plate bitmap.
     * @param id plate color id.
     */
    public Plate(int x, int y , Bitmap bitmap ,int id) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.bitmap = bitmap;
        //set the width of plate.
        plateHeight = bitmap.getHeight();
        //set the height of plate.
        plateWidth = bitmap.getWidth();
        //at the start the statue of plate is inside the game view bounds.
        status = GameUtils.STATUS.IN_SIDE;
    }

    /**
     * move the plate by increase the x and y.
     * then change it status due to its position according to game view bounds.
     * @param yLeft the y of the top plate catch by clown left hand.
     * @param yRight the y of the top plate catch by clown right hand.
     * @return the new status of plate
     */
    public GameUtils.STATUS update(int yLeft , int yRight) {
        return status;
    }

    /**
     * change the status of plate due to its position according to game view bounds.
     * @param yLeft the y of the top plate catch by clown left hand.
     * @param yRight the y of the top plate catch by clown right hand.
     */
    public void check(int yLeft , int yRight) {

        if (x < (-1*plateWidth) || x > GameUtils.getViewWidth() || y > GameUtils.getViewHeight()) {
            //The plate went out of its defined limits
            status = GameUtils.STATUS.OUT_SIDE;
        } else {
            //check if the clown catch the plate.
            int x1 = Clown.getInstance().getX(); //the x position of clown left hand.
            int x2 = x1 + Clown.getInstance().getWidth() - plateWidth; //the x position of clown right hand.
            int y_dash = y + plateHeight;
            if(x >= x1-10 && x <= x1+10 && y_dash <= yLeft+15 && y_dash >= yLeft-15) {
                //the clown left hand catch the plate.
                status = GameUtils.STATUS.LEFT_CATCH;
            } else if(x >= x2-10 && x <= x2+10 && y_dash <= yRight+15 && y_dash >= yRight-15) {
                //the clown right hand catch the plate.
                status = GameUtils.STATUS.RIGHT_CATCH;
            }
        }
    }

    /**
     * @return position of plate according to the x axis.
     */
    public int getX() {
        return x;
    }

    /**
     * @return position of plate according to the y axis.
     */
    public int getY() {
        return y;
    }

    /**
     * @return the plate color id.
     */
    public int getId() {
        return id;
    }

    /**
     * @return the plate width.
     */
    public int getPlateWidth() {
        return plateWidth;
    }

    /**
     * @return return plate height.
     */
    public int getPlateHeight() {
        return plateHeight;
    }

    /**
     * @return the bitmap of the plate.
     */
    public Bitmap getBitmap() {
        return bitmap;
    }
}
