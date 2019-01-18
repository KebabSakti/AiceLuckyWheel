package com.vjtechsolution.aiceluckywheel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private Button spinBtn;
    private FrameLayout wheel;

    private FrameLayout frameLayout;
    private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12;

    // sectors of our wheel (look at the image to see the sectors)
    private static final String[] sectors = {
            "Milk Melon","Bingo","Funky Susu","Nanas","Zonk","Zonk","Zonk","Zonk","Zonk","Zonk","Zonk","Ice Sugar"
    };

    // We create a Random instance to make our wheel spin randomly
    private static final Random RANDOM = new Random();
    private int degree = 0, degreeOld = 0;
    // We have 37 sectors on the wheel, we divide 360 by this value to have angle for each sector
    // we divide by 2 to have a half sector
    private static final float HALF_SECTOR = 360f / 12f / 2f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        wheel = findViewById(R.id.wheel);
        spinBtn = findViewById(R.id.spin_btn);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);
        tv7 = findViewById(R.id.tv7);
        tv8 = findViewById(R.id.tv8);
        tv9 = findViewById(R.id.tv9);
        tv10 = findViewById(R.id.tv10);
        tv11 = findViewById(R.id.tv11);
        tv12 = findViewById(R.id.tv12);

        tv1.setX(1);
        tv1.setY(-170);
        tv1.setRotation(-90);

        tv2.setX(80);
        tv2.setY(-150);
        tv2.setRotation(-60);

        tv3.setX(125);
        tv3.setY(-75);
        tv3.setRotation(-35);

        tv4.setX(180);
        tv4.setY(-5);
        tv4.setRotation(0);

        tv5.setX(120);
        tv5.setY(65);
        tv5.setRotation(35);

        tv6.setText("Zonk");
        tv6.setX(60);
        tv6.setY(100);
        tv6.setRotation(60);

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
                        spinBtn.setEnabled(false);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // we display the correct sector pointed by the triangle at the end of the rotate animation
                        spinBtn.setEnabled(true);
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

            Log.d("DEGREE", String.valueOf(degrees));
            Log.d("START", String.valueOf(start));
            Log.d("END", String.valueOf(end));

        } while (text == null  &&  i < sectors.length);

        return text;
    }
}
