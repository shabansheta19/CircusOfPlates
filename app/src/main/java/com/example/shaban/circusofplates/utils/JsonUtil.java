package com.example.shaban.circusofplates.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.example.shaban.circusofplates.modle.Clown;
import com.example.shaban.circusofplates.modle.plate.Plate;
import com.example.shaban.circusofplates.modle.plate.TopPlate;
import com.example.shaban.circusofplates.view.GameView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    public static String toJSon(GameView gameView) {

        List<Plate> in = gameView.getInSidePlates();
        List<Plate> leftCatch = gameView.getCatchPlatesLeft();
        List<Plate> rightCatch = gameView.getCatchPlatesRight();

        try {
            JSONObject root = new JSONObject(); //the root object of the json file.
            root.put(Constants.GAME_SCORE,gameView.getScore()); //put the score.
            root.put(Constants.GAME_TIMER_COUNTER,gameView.getTimerCounter()); //put the timer counter.
            root.put(Constants.GAME_CLOWN_X, Clown.getInstance().getX()); //put the x position of the clown.

            // In this case we need a json array to hold the java list of plates
            //convert the in side plates list to json array.
            JSONArray jsonInArr = new JSONArray();
            for (Plate p : in) {
                JSONObject plateObj = new JSONObject();
                plateObj.put(Constants.PLATE_X,p.getX());
                plateObj.put(Constants.PLATE_Y,p.getY());
                plateObj.put(Constants.PLATE_COLOR_ID,p.getId());
                jsonInArr.put(plateObj);
            }
            root.put(Constants.GAME_IN_SIDE_PLATES,jsonInArr);

            //convert the in side plates list to json array.
            JSONArray jsonLeftArr = new JSONArray();
            for (Plate p : leftCatch) {
                JSONObject plateObj = new JSONObject();
                plateObj.put(Constants.PLATE_X,p.getX());
                plateObj.put(Constants.PLATE_Y,p.getY());
                plateObj.put(Constants.PLATE_COLOR_ID,p.getId());
                jsonLeftArr.put(plateObj);
            }
            root.put(Constants.GAME_LEFT_CATCH_PLATES,jsonLeftArr);

            //convert the in side plates list to json array.
            JSONArray jsonRightArr = new JSONArray();
            for (Plate p : rightCatch) {
                JSONObject plateObj = new JSONObject();
                plateObj.put(Constants.PLATE_X,p.getX());
                plateObj.put(Constants.PLATE_Y,p.getY());
                plateObj.put(Constants.PLATE_COLOR_ID,p.getId());
                jsonRightArr.put(plateObj);
            }
            root.put(Constants.GAME_RIGHT_CATCH_PLATES,jsonRightArr);

            //return the text of json file
            return root.toString();

        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static boolean parseJSON (Context context, String jsonText, GameView gameView) {
        try {
            JSONObject root = new JSONObject(jsonText); //get the root object of the json file.
            gameView.setScore(root.getInt(Constants.GAME_SCORE)); //set the loaded game score.
            gameView.setTimerCounter(root.getInt(Constants.GAME_TIMER_COUNTER)); //set the loaded counter of the timer.
            Clown.getInstance().setX(root.getInt(Constants.GAME_CLOWN_X)); //set the colwn x axis position.

            //get the in side plates.
            List<Plate> inSidePlates = new ArrayList<>();
            JSONArray jsonInArr = root.getJSONArray(Constants.GAME_IN_SIDE_PLATES);
            for (int i=0; i < jsonInArr.length(); i++) {
                JSONObject plateJsonObj = jsonInArr.getJSONObject(i);
                int colorId = plateJsonObj.getInt(Constants.PLATE_COLOR_ID);
                int x = plateJsonObj.getInt(Constants.PLATE_X);
                int y = plateJsonObj.getInt(Constants.PLATE_Y);
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),PlateUtils.platesDrawables[colorId]);
                Plate plate = new TopPlate(x,y,bitmap,colorId);
                inSidePlates.add(plate);
            }
            gameView.setInSidePlates(inSidePlates);

            //get the left catch plates.
            List<Plate> leftCatchPlates = new ArrayList<>();
            JSONArray jsonLeftArr = root.getJSONArray(Constants.GAME_LEFT_CATCH_PLATES);
            for (int i=0; i < jsonLeftArr.length(); i++) {
                JSONObject plateJsonObj = jsonLeftArr.getJSONObject(i);
                int colorId = plateJsonObj.getInt(Constants.PLATE_COLOR_ID);
                int x = plateJsonObj.getInt(Constants.PLATE_X);
                int y = plateJsonObj.getInt(Constants.PLATE_Y);
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),PlateUtils.platesDrawables[colorId]);
                Plate plate = new TopPlate(x,y,bitmap,colorId);
                leftCatchPlates.add(plate);
            }
            gameView.setCatchPlatesLeft(leftCatchPlates);

            //get the right catch plates.
            List<Plate> rightCatchPlates = new ArrayList<>();
            JSONArray jsonRightArr = root.getJSONArray(Constants.GAME_RIGHT_CATCH_PLATES);
            for (int i=0; i < jsonRightArr.length(); i++) {
                JSONObject plateJsonObj = jsonRightArr.getJSONObject(i);
                int colorId = plateJsonObj.getInt(Constants.PLATE_COLOR_ID);
                int x = plateJsonObj.getInt(Constants.PLATE_X);
                int y = plateJsonObj.getInt(Constants.PLATE_Y);
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),PlateUtils.platesDrawables[colorId]);
                Plate plate = new TopPlate(x,y,bitmap,colorId);
                rightCatchPlates.add(plate);
            }
            gameView.setCatchPlatesRight(rightCatchPlates);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
