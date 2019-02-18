package com.vjtechsolution.aiceluckywheel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
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

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView kesempatan, drawn, win, lost;

    private Integer drawnTotal = 0;
    private Integer total = 0;
    private Integer winTotal = 0;
    private Integer lostTotal = 0;
    private String session;
    private String no_telp;
    private String result = "";

    private ArrayList<String> spinRes = new ArrayList<>();

    private ArrayList<Integer> beli = new ArrayList<>();
    private ArrayList<Integer> drawnRes = new ArrayList<>();
    private ArrayList<Integer> menang = new ArrayList<>();
    private ArrayList<Integer> kalah = new ArrayList<>();
    private ArrayList<String> hadiah = new ArrayList<>();

    private LottieAnimationView winAnimation;

    private Integer luck = 5;

    private SoundPool soundPool;
    private int winSoundId;
    private int lostSoundId;
    private int spinSoundId;
    private boolean isWinSoundLoad = false;
    private boolean isLostSoundLoad = false;
    private boolean isSpinSoundLoad = false;
    private Timer timer;

    private Intent intent;

    private Button spinBtn, endBtn;
    private FrameLayout wheel;

    private SweetAlertDialog pDialog;

    private SharedPreferences sharedPreferences;
    private String username, kode_asset, api_token;

    private ArrayList<String> prizeList = new ArrayList<>();
    private ArrayList<String> shuffled;
    private List<PrizeData> prizeData;
    private HashMap<String, Integer> mapPrize = new HashMap<>();

    private int sectorW, sectorZ;

    private ArrayList<Double> winSector = new ArrayList<>();
    private ArrayList<Double> zonkSector = new ArrayList<>();

    private ArrayList<GamePlayData> gamePlayDatas = new ArrayList<>();

    private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12;

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

        sharedPreferences = getSharedPreferences(getString(R.string.key_preference), Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username","");
        api_token = sharedPreferences.getString("api_token", "");
        kode_asset = sharedPreferences.getString("kode_asset", "");

        //progress dialog
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorAccent));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

        kesempatan = findViewById(R.id.kesempatan);
        drawn = findViewById(R.id.drawn);
        win = findViewById(R.id.win);
        lost = findViewById(R.id.lost);
        wheel = findViewById(R.id.wheel);
        spinBtn = findViewById(R.id.spin_btn);
        endBtn = findViewById(R.id.res_btn);

        winAnimation = findViewById(R.id.star_success);

        total = Math.round(intent.getIntExtra("total", 0) / 2);
        session = intent.getStringExtra("session");
        no_telp = intent.getStringExtra("no_telp");

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

        spinBtn.setOnClickListener(this);
        endBtn.setOnClickListener(this);

        //ready the sound
        prepareSound(this);

        tv1.setX(1);
        tv1.setY(-140);
        tv1.setRotation(-90);

        tv2.setX(80);
        tv2.setY(-120);
        tv2.setRotation(-60);

        tv3.setX(125);
        tv3.setY(-75);
        tv3.setRotation(-35);

        tv4.setX(160);
        tv4.setY(-5);
        tv4.setRotation(0);

        tv5.setX(120);
        tv5.setY(65);
        tv5.setRotation(35);

        tv6.setX(60);
        tv6.setY(100);
        tv6.setRotation(60);

        tv7.setX(0);
        tv7.setY(120);
        tv7.setRotation(90);

        tv8.setX(-70);
        tv8.setY(110);
        tv8.setRotation(300);

        tv9.setX(-110);
        tv9.setY(60);
        tv9.setRotation(-30);

        tv10.setX(-120);
        tv10.setY(-4);
        tv10.setRotation(0);

        tv11.setX(-110);
        tv11.setY(-65);
        tv11.setRotation(25);

        tv12.setX(-60);
        tv12.setY(-110);
        tv12.setRotation(60);

        getPrizeData();
    }

    private void prepareSound(Context context){
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        winSoundId = soundPool.load(context, R.raw.game_win, 1);
        lostSoundId = soundPool.load(context, R.raw.game_lost, 1);
        spinSoundId = soundPool.load(context, R.raw.game_spin, 1);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if(status == 0){
                    switch (sampleId)
                    {
                        case 2:
                            isWinSoundLoad = true;
                            break;

                        case 3:
                            isLostSoundLoad = true;
                            break;

                        case 4:
                            isSpinSoundLoad = true;
                            break;
                    }
                }
            }
        });
    }

    private void getPrizeData() {
        pDialog.show();

        //add one result to game play data array
        //gamePlayDatas.add(new GamePlayData(session, no_telp, kode_asset, "",total, 0, 0, 0));

        final PrizeModel prizeModel = new PrizeModel(
                username,
                api_token,
                kode_asset
        );

        GetPrize getPrize = RetrofitBuilderGenerator.createService(GetPrize.class);
        Call<PrizeModel> prizeModelCall = getPrize.prizeModel(prizeModel);

        prizeModelCall.enqueue(new Callback<PrizeModel>() {
            @Override
            public void onResponse(Call<PrizeModel> call, Response<PrizeModel> response) {
                if(response.code() == 200){
                    prizeData = response.body().getData();

                    for(int i=0; i < prizeData.size(); i++){
                        prizeList.add(prizeData.get(i).getProduct().getNama());

                        //prepare prize map
                        mapPrize.put(prizeData.get(i).getProduct().getNama(), prizeData.get(i).getPrize_stock());
                    }

                    for(int s=0; s < 12-prizeData.size(); s++){
                        prizeList.add("Zonk");
                    }

                    shuffleThePrize();
                }else{

                    pDialog.dismissWithAnimation();
                }
            }

            @Override
            public void onFailure(Call<PrizeModel> call, Throwable t) {

                pDialog.dismissWithAnimation();
            }
        });
    }

    private void shuffleThePrize() {
        Collections.shuffle(prizeList);

        shuffled = prizeList;

        for(int i=0; i < shuffled.size(); i++){
            if(!shuffled.get(i).equals("Zonk")){
                Double sWin = (Double.valueOf(i+1)) / 12;
                winSector.add(sWin * 360);
                sectorW = 360 - (int) Math.round(winSector.get(0));

                //Log.d("Prize Won", String.valueOf(sectorW));
            }else{
                Double sZonk = (Double.valueOf(i+1)) / 12;
                zonkSector.add(sZonk * 360);
                sectorZ = 360 - (int) Math.round(zonkSector.get(0));

                //Log.d("Prize Zonk", String.valueOf(sectorZ));
            }
        }

        Log.d("Prize Shuff", String.valueOf(shuffled));
        Log.d("Prize Won", String.valueOf(winSector));
        Log.d("Prize Zonk", String.valueOf(zonkSector));

        tv1.setText(shuffled.get(11));
        tv2.setText(shuffled.get(0));
        tv3.setText(shuffled.get(1));
        tv4.setText(shuffled.get(2));
        tv5.setText(shuffled.get(3));
        tv6.setText(shuffled.get(4));
        tv7.setText(shuffled.get(5));
        tv8.setText(shuffled.get(6));
        tv9.setText(shuffled.get(7));
        tv10.setText(shuffled.get(8));
        tv11.setText(shuffled.get(9));
        tv12.setText(shuffled.get(10));

        pDialog.hide();
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

            //Log.d("Prize SECTOR", String.valueOf(degrees));

            i++;

        } while (text == null  &&  i < shuffled.size());

        return text;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.spin_btn:
                //play spin sound

                if(total < 1){
                    Toast.makeText(GameActivity.this, "Kesempatan anda telah habis", Toast.LENGTH_SHORT).show();
                }else {
                    degreeOld = degree % 360;
                    // we calculate random angle for rotation of our wheel
                    //degree = RANDOM.nextInt(360) + 360;
                    //degree = (int) Math.round(winSector.get(0)) + 360;

                    if(RANDOM.nextInt(100) > luck){
                        //zonk
                        degree = 720 + (360 - (int) Math.round(zonkSector.get(RANDOM.nextInt(zonkSector.size()))));
                    }else{
                        //win
                        degree = 720 + (360 - (int) Math.round(winSector.get(RANDOM.nextInt(winSector.size()))));
                    }

                    // rotation effect on the center of the wheel
                    RotateAnimation rotateAnim = new RotateAnimation(degreeOld, degree,
                            RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                    rotateAnim.setDuration(3600);
                    rotateAnim.setFillAfter(true);
                    rotateAnim.setInterpolator(new DecelerateInterpolator());
                    rotateAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            //add result to game play data array
                            gamePlayDatas.add(new GamePlayData(session, no_telp, kode_asset, result, total, drawnTotal, winTotal, lostTotal));

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
                            //timer.cancel();

                            // we display the correct sector pointed by the triangle at the end of the rotate animation
                            result = getSector(360 - (degree % 360));

                            spinRes.add(result);

                            Log.d("GAME SECTOR", result);

                            if(result != null) {
                                if (result.equals("Zonk")) {
                                    lostTotal += 1;

                                    if(luck <= 100) {
                                        luck += 5;
                                    }else{
                                        luck = 100;
                                    }

                                } else {
                                    winAnimation.setAnimation("ribbon.json");
                                    winAnimation.playAnimation();

                                    winTotal += 1;

                                    luck = 5;

                                    //check stock win prize
                                    if(mapPrize.get(result) > 0) {
                                        mapPrize.put(result, mapPrize.get(result) - 1);
                                    }else{
                                        int totStock = 0;
                                        winSector = new ArrayList<>();
                                        for (Map.Entry<String, Integer> entry : mapPrize.entrySet())
                                        {
                                            //System.out.println(entry.getKey() + "/" + entry.getValue());

                                            totStock += entry.getValue();

                                            if(totStock > 0) {
                                                if (entry.getValue() > 0) {
                                                    for (int i = 0; i < shuffled.size(); i++) {
                                                        if (!shuffled.get(i).equals(entry.getKey())) {
                                                            Double sWin = (Double.valueOf(i + 1)) / 12;
                                                            winSector.add(sWin * 360);
                                                            sectorW = 360 - (int) Math.round(winSector.get(0));

                                                            Log.d("Prize Won NEWW", String.valueOf(sectorW));
                                                        }
                                                    }
                                                }
                                            }else{
                                                Toast.makeText(GameActivity.this, "Maaf.. Stok hadiah sudah habis", Toast.LENGTH_SHORT).show();

                                                //simpan ke server
                                                saveGameResult();
                                            }
                                        }
                                    }
                                }
                            }

                            //Toast.makeText(GameActivity.this, result, Toast.LENGTH_SHORT).show();

                            if(result != null) {
                                if (!result.equals("Zonk")) {
                                    //play winning sound
                                    if(isWinSoundLoad){
                                        soundPool.play(winSoundId,1,1,1,0,1);
                                    }

                                    //show win dialogue
                                    new SweetAlertDialog(GameActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Selamat!")
                                            .setContentText("Anda mendapatkan " + result)
                                            .show();
                                }else{
                                    //play lost sound
                                    if(isLostSoundLoad){
                                        soundPool.play(lostSoundId, 1,1,1,0,1);
                                    }

                                    //show lost dialogue
                                    new SweetAlertDialog(GameActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Maaf")
                                            .setContentText("Anda belum beruntung")
                                            .show();
                                }
                            }

                            //jika kesempatan spin habis, simpan ke server
                            if(total < 1){
                                spinBtn.setVisibility(View.GONE);
                                endBtn.setVisibility(View.VISIBLE);

                                gamePlayDatas.add(new GamePlayData(session, no_telp, kode_asset, result, total, drawnTotal, winTotal, lostTotal));

                                saveGameResult();
                            }

                            win.setText("Win : "+winTotal);
                            lost.setText("Lost : "+lostTotal);

                            spinBtn.setText("Putar Roda");
                            spinBtn.setEnabled(true);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    // we start the animation
                    wheel.startAnimation(rotateAnim);
                }
                break;


            case R.id.res_btn:
                Toast.makeText(GameActivity.this, String.valueOf(spinRes), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void saveGameResult() {
        pDialog.setTitleText("Uploading");
        pDialog.setContentText("Mohon tunggu, menyimpan data ke server");
        pDialog.show();

        for(int i = 0; i < gamePlayDatas.size(); i++){
            beli.add(gamePlayDatas.get(i).getBeli());
            drawnRes.add(gamePlayDatas.get(i).getDrawn());
            menang.add(gamePlayDatas.get(i).getMenang());
            kalah.add(gamePlayDatas.get(i).getKalah());
            hadiah.add(gamePlayDatas.get(i).getHadiah());
        }

        Log.d("GAME BELI", String.valueOf(beli)+" : "+String.valueOf(beli.size()));
        Log.d("GAME DRAWN", String.valueOf(drawnRes)+" : "+String.valueOf(drawnRes.size()));
        Log.d("GAME MENANG", String.valueOf(menang)+" : "+String.valueOf(menang.size()));
        Log.d("GAME KALAH", String.valueOf(kalah)+" : "+String.valueOf(kalah.size()));
        Log.d("GAME HADIAH", String.valueOf(hadiah)+" : "+String.valueOf(hadiah.size()));

        PostGameResult postGameResult = RetrofitBuilderGenerator.createService(PostGameResult.class);
        Call<GameResultData> gameResultDataCall = postGameResult.postGameResult(username, api_token, kode_asset, session, no_telp, beli, drawnRes, menang, kalah, hadiah);

        gameResultDataCall.enqueue(new Callback<GameResultData>() {
            @Override
            public void onResponse(Call<GameResultData> call, Response<GameResultData> response) {

                //Log.d("GAME DEBUG", String.valueOf(response.body().getMessage()));

                if(response.code() == 200){

                    Toast.makeText(GameActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    pDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
                    pDialog.setTitleText("Hasil Spin");
                    pDialog.setContentText(String.valueOf(spinRes));
                    pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            pDialog.dismissWithAnimation();

                            finish();
                        }
                    });

                }else{
                    Toast.makeText(GameActivity.this, "Fail! CODE != 200 : "+String.valueOf(response.body()), Toast.LENGTH_SHORT).show();
                    pDialog.dismissWithAnimation();
                }
            }

            @Override
            public void onFailure(Call<GameResultData> call, Throwable t) {
                Toast.makeText(GameActivity.this, "Fail! NO CODE : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismissWithAnimation();
            }
        });

    }

    public void onDestroy(){
        super.onDestroy();

        soundPool.release();
        soundPool = null;
    }
}
