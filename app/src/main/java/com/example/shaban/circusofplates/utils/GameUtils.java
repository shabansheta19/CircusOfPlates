package com.example.shaban.circusofplates.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.shaban.circusofplates.R;

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

    public static void showExitConfirm(final Activity context) {
        final Dialog dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.exit_dialog_layout);

        Button noBtn = (Button)dialog.findViewById(R.id.exit_no_btn);
        Button yesBtn = (Button)dialog.findViewById(R.id.exit_yes_btn);

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
