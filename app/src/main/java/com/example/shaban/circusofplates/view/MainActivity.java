package com.example.shaban.circusofplates.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.shaban.circusofplates.R;
import com.example.shaban.circusofplates.utils.Constants;
import com.example.shaban.circusofplates.utils.GameUtils;

public class MainActivity extends AppCompatActivity {

    private ImageView logoImageView;  //the image which appear using animation on the start of game.
    private RelativeLayout activityLayout; //the reference to the activity layout.
    private Button startGameBtn; //object of button to start game view activity.
    private Button loadGameBtn; //object of button to load saved game.
    private Button exitBtn; //object of button to close the game.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bind the object of button with the button view at the xml file.
        startGameBtn = (Button)findViewById(R.id.start_game_btn);
        loadGameBtn = (Button)findViewById(R.id.load_game_btn);
        exitBtn = (Button)findViewById(R.id.exit_btn);

        //start the game view activity when click play game button.
        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GameActivity.class);
                intent.putExtra(Constants.GAME_MODE,false);
                startActivity(intent);
                finish();
            }
        });

        //start the game view activity when click load game button.
        loadGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GameActivity.class);
                intent.putExtra(Constants.GAME_MODE,true);
                startActivity(intent);
                finish();
            }
        });

        //hide the home buttons
        startGameBtn.setVisibility(View.INVISIBLE);
        loadGameBtn.setVisibility(View.INVISIBLE);
        exitBtn.setVisibility(View.INVISIBLE);

        //layout bind view.
        activityLayout = (RelativeLayout)findViewById(R.id.activity_main);
        //declaration of logo image view object.
        logoImageView = new ImageView(this);
        //setting logo image object resource
        logoImageView.setImageResource(R.drawable.logo);
        //setting logo image object position
        logoImageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        //add the logo image view to the activity layout
        activityLayout.addView(logoImageView);

        //start the animation of starting game.
        enteringGameAnimation();
    }

    /**
     * the animation of logo game image.
     */
    private void enteringGameAnimation() {
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.backgound_fade_in);
        logoImageView.startAnimation(fadeIn);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Animation fadeOut = AnimationUtils.loadAnimation(MainActivity.this, R.anim.backgound_fade_out);
                logoImageView.startAnimation(fadeOut);
                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        activityLayout.removeView(logoImageView);
                        activityLayout.setBackgroundResource(R.drawable.bg);
                        homeBtnsAnimation();
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    /**
     * appear each button at the game start by animation.
     */
    private void homeBtnsAnimation() {
        Animation bounce = AnimationUtils.loadAnimation(this,R.anim.bounce);
        exitBtn.setVisibility(View.VISIBLE);
        exitBtn.startAnimation(bounce);
        loadGameBtn.setVisibility(View.VISIBLE);
        loadGameBtn.startAnimation(bounce);
        startGameBtn.setVisibility(View.VISIBLE);
        startGameBtn.startAnimation(bounce);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            GameUtils.showExitConfirm(this);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
