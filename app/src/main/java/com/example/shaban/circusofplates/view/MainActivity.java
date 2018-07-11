package com.example.shaban.circusofplates.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.shaban.circusofplates.R;

public class MainActivity extends AppCompatActivity {

    private ImageView logoImageView;
    private RelativeLayout activityLayout;
    private Button startGameBtn;
    private Button resumeGameBtn;
    private Button settingsBtn;
    private Button exitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar(enable full screen mode).
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //setup home buttons
        startGameBtn = (Button)findViewById(R.id.start_game_btn);
        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,GameActivity.class));
            }
        });
        resumeGameBtn = (Button)findViewById(R.id.resume_game_btn);
        settingsBtn = (Button)findViewById(R.id.settings_btn);
        exitBtn = (Button)findViewById(R.id.exit_btn);

        //hide the home buttons
        startGameBtn.setVisibility(View.INVISIBLE);
        resumeGameBtn.setVisibility(View.INVISIBLE);
        settingsBtn.setVisibility(View.INVISIBLE);
        exitBtn.setVisibility(View.INVISIBLE);

        //layout setup
        activityLayout = (RelativeLayout)findViewById(R.id.activity_main);
        //ImageView Setup
        logoImageView = new ImageView(this);
        //setting image resource
        logoImageView.setImageResource(R.drawable.logo);
        //setting image position
        logoImageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        //add the image view
        activityLayout.addView(logoImageView);

        enteringGameAnimation();
    }

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

    private void homeBtnsAnimation() {
        Animation bounce = AnimationUtils.loadAnimation(this,R.anim.bounce);
        exitBtn.setVisibility(View.VISIBLE);
        exitBtn.startAnimation(bounce);
        settingsBtn.setVisibility(View.VISIBLE);
        settingsBtn.startAnimation(bounce);
        resumeGameBtn.setVisibility(View.VISIBLE);
        resumeGameBtn.startAnimation(bounce);
        startGameBtn.setVisibility(View.VISIBLE);
        startGameBtn.startAnimation(bounce);
    }
}
