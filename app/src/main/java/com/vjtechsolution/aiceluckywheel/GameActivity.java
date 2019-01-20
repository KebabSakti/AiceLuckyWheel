package com.vjtechsolution.aiceluckywheel;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView kesempatan, drawn, win, lost;

    private Integer drawnTotal;
    private Integer total;
    private Integer winTotal;
    private Integer lostTotal;

    private ArrayList<String> spinRes = new ArrayList<>();

    private Intent intent;

    private Button spinBtn, endBtn;
    private FrameLayout wheel;

    private FrameLayout frameLayout;
    private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12;

    // sectors of our wheel (look at the image to see the sectors)
    private static final String[] sectors = {
            "Ice Sugar","Bingo","Funky Susu","Nanas","Zonk","Zonk","Mochi","Zonk","Zonk","Zonk","Zonk","Milk Melon"
    };

    private ArrayList<String>  sectorList = new ArrayList<>();

    private ArrayList<String> shuffled;

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

        intent = getIntent();

        total = intent.getIntExtra("total", 0);
        drawnTotal = 0;
        winTotal = 0;
        lostTotal = 0;

        for(int i=0;i<sectors.length;i++){
            sectorList.add(sectors[i]);
        }

        Collections.shuffle(sectorList);

        shuffled = sectorList;

        kesempatan = findViewById(R.id.kesempatan);
        drawn = findViewById(R.id.drawn);
        win = findViewById(R.id.win);
        lost = findViewById(R.id.lost);
        wheel = findViewById(R.id.wheel);
        spinBtn = findViewById(R.id.spin_btn);
        endBtn = findViewById(R.id.res_btn);

        kesempatan.setText("Chance : "+total);
        drawn.setText("Drawn : "+drawnTotal);
        win.setText("Win : "+winTotal);
        lost.setText("Lost : "+lostTotal);

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

        tv1.setText(shuffled.get(11));
        tv1.setX(1);
        tv1.setY(-170);
        tv1.setRotation(-90);

        tv2.setText(shuffled.get(0));
        tv2.setX(80);
        tv2.setY(-150);
        tv2.setRotation(-60);

        tv3.setText(shuffled.get(1));
        tv3.setX(125);
        tv3.setY(-75);
        tv3.setRotation(-35);

        tv4.setText(shuffled.get(2));
        tv4.setX(180);
        tv4.setY(-5);
        tv4.setRotation(0);

        tv5.setText(shuffled.get(3));
        tv5.setX(120);
        tv5.setY(65);
        tv5.setRotation(35);

        tv6.setText(shuffled.get(4));
        tv6.setX(60);
        tv6.setY(100);
        tv6.setRotation(60);

        tv7.setText(shuffled.get(5));
        tv7.setX(0);
        tv7.setY(120);
        tv7.setRotation(90);

        tv8.setText(shuffled.get(6));
        tv8.setX(-70);
        tv8.setY(110);
        tv8.setRotation(300);

        tv9.setText(shuffled.get(7));
        tv9.setX(-110);
        tv9.setY(60);
        tv9.setRotation(-30);

        tv10.setText(shuffled.get(8));
        tv10.setX(-120);
        tv10.setY(-4);
        tv10.setRotation(0);

        tv11.setText(shuffled.get(9));
        tv11.setX(-110);
        tv11.setY(-65);
        tv11.setRotation(25);

        tv12.setText(shuffled.get(10));
        tv12.setX(-60);
        tv12.setY(-110);
        tv12.setRotation(60);

        spinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(total < 1){
                    Toast.makeText(GameActivity.this, "Kesempatan anda telah habis", Toast.LENGTH_SHORT).show();
                }else {
                    degreeOld = degree % 360;
                    // we calculate random angle for rotation of our wheel
                    degree = RANDOM.nextInt(360) + 2000;
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
                            total -= 1;
                            drawnTotal += 1;

                            kesempatan.setText("Chance : " + total);
                            drawn.setText("Drawn : " + drawnTotal);

                            spinBtn.setText("Memutar..");
                            spinBtn.setEnabled(false);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            // we display the correct sector pointed by the triangle at the end of the rotate animation
                            String result = getSector(360 - (degree % 360));

                            if(result.equals("Zonk")){
                                lostTotal += 1;
                            }else{
                                winTotal += 1;
                            }

                            spinRes.add(result);

                            win.setText("Win : "+winTotal);
                            lost.setText("Lost : "+lostTotal);

                            spinBtn.setText("Putar Roda");
                            spinBtn.setEnabled(true);

                            Toast.makeText(GameActivity.this, result, Toast.LENGTH_SHORT).show();

                            if(total < 1){
                                spinBtn.setVisibility(View.GONE);
                                endBtn.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    // we start the animation
                    wheel.startAnimation(rotateAnim);
                }
            }
        });

        //akhiri game dan launch halaman record
        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GameActivity.this, String.valueOf(spinRes), Toast.LENGTH_SHORT).show();
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
                //text = sectors[i];
                text = shuffled.get(i);
            }

            i++;
            // now we can test our Android Roulette Game :)
            // That's all !
            // In the second part, you will learn how to add some bets on the table to play to the Roulette Game :)
            // Subscribe and stay tuned !

            Log.d("DEGREE", String.valueOf(degrees));
            Log.d("START", String.valueOf(start));
            Log.d("END", String.valueOf(end));

        } while (text == null  &&  i < shuffled.size());

        return text;
    }
}
