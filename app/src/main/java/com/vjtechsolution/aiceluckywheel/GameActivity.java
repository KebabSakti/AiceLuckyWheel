package com.vjtechsolution.aiceluckywheel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private Button spinBtn;
    private ImageView wheel;

    private FrameLayout frameLayout;
    private TextView tv1;

    // We create a Random instance to make our wheel spin randomly
    private static final Random RANDOM = new Random();
    private int degree = 0, degreeOld = 0;
    // We have 37 sectors on the wheel, we divide 360 by this value to have angle for each sector
    // we divide by 2 to have a half sector
    private static final float HALF_SECTOR = 360f / 12f / 2f;

    // sectors of our wheel (look at the image to see the sectors)
    private static final String[] sectors = {
            "Kuning","Himut","Hitut","Bimut","Bimut2","Bitut","Bitut2","Ungu","PINK","Merah","OrangeTut","Orangec3"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        wheel = findViewById(R.id.wheel);
        spinBtn = findViewById(R.id.spin_btn);

        //frameLayout = findViewById(R.id.fl);
        //tv1 = findViewById(R.id.tv1);

        /*
        tv1.setX(0);
        tv1.setY(-250);
        tv1.setRotation(90);

        tv1.setX(45);
        tv1.setY(-250);
        tv1.setRotation(100);
        */

        spinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                degreeOld = degree % 360;
                // we calculate random angle for rotation of our wheel
                degree = RANDOM.nextInt(360) + 1080;
                // rotation effect on the center of the wheel
                RotateAnimation rotateAnim = new RotateAnimation(degreeOld, degree,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotateAnim.setDuration(3600);
                rotateAnim.setFillAfter(true);
                rotateAnim.setInterpolator(new DecelerateInterpolator());
                rotateAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // we empty the result text view when the animation start
                        //resultTv.setText("");
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // we display the correct sector pointed by the triangle at the end of the rotate animation
                        //resultTv.setText(getSector(360 - (degree % 360)));
                        Toast.makeText(GameActivity.this, String.valueOf(getSector(360 - (degree % 360))), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                // we start the animation
                wheel.startAnimation(rotateAnim);
            }
        });
    }

    private String getSector(int degrees) {
        int i = 0;
        String text = null;

        do {
            // start and end of each sector on the wheel
            float start = HALF_SECTOR * (i * 2 + 1);
            float end = HALF_SECTOR * (i * 2 + 3);

            if (degrees >= start && degrees < end) {
                // degrees is in [start;end[
                // so text is equals to sectors[i];
                text = sectors[i];
            }

            i++;
            // now we can test our Android Roulette Game :)
            // That's all !
            // In the second part, you will learn how to add some bets on the table to play to the Roulette Game :)
            // Subscribe and stay tuned !

        } while (text == null  &&  i < sectors.length);

        return text;
    }
}
