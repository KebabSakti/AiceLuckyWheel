package com.vjtechsolution.aiceluckywheel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arasthel.asyncjob.AsyncJob;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView dimainkan, dispin, kustomer, menang, hadiah, kalah;
    private ImageView play;
    private Button logout;

    private String username, api_token, kode_asset;

    private CardView adsContainer;
    private ImageView adsCloseBtn;
    private ImageView adsImage;

    private String adsUrl = "";

    private Context context;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        context = this;

        sharedPreferences = context.getSharedPreferences(getString(R.string.key_preference), Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username","");
        api_token = sharedPreferences.getString("api_token", "");
        kode_asset = sharedPreferences.getString("kode_asset", "");

        dimainkan = findViewById(R.id.dimainkan);
        dispin = findViewById(R.id.dispin);
        kustomer = findViewById(R.id.kustomer);
        menang = findViewById(R.id.menang);
        hadiah = findViewById(R.id.hadiah);
        kalah = findViewById(R.id.kalah);
        play = findViewById(R.id.play);
        logout = findViewById(R.id.logout);

        //ads
        adsContainer = findViewById(R.id.ads_container);
        adsCloseBtn = findViewById(R.id.ads_close_icon);
        adsImage = findViewById(R.id.ads_img);

        play.setOnClickListener(this);
        logout.setOnClickListener(this);
        adsCloseBtn.setOnClickListener(this);
    }

    public void onResume(){
        super.onResume();

        getDashboardData();

        getAds();
    }

    private void getAds(){
        AdsModel adsModel = new AdsModel(username, api_token, kode_asset);

        final AdsInterface adsInterface = RetrofitBuilderGenerator.createService(AdsInterface.class);
        Call<AdsModel> adsModelCall = adsInterface.adsModel(adsModel);

        adsModelCall.enqueue(new Callback<AdsModel>() {
            @Override
            public void onResponse(Call<AdsModel> call, Response<AdsModel> response) {

                if(response.code() == 200){
                    if(response.body().getStatus()){
                        adsUrl = response.body().getData().getFile();

                        AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                            @Override
                            public void doInUIThread() {
                                //load ads
                                Picasso.get().load(adsUrl).into(adsImage);

                                //Animation topBtm = AnimationUtils.loadAnimation(context, R.anim.slide_in_top);
                                //adsContainer.startAnimation(topBtm);

                                adsContainer.setVisibility(View.VISIBLE);
                            }
                        });

                    }
                }
            }

            @Override
            public void onFailure(Call<AdsModel> call, Throwable t) {
                Toast.makeText(context, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDashboardData() {
        String load = "Loading..";

        dimainkan.setText(load);
        dispin.setText(load);
        kustomer.setText(load);
        menang.setText(load);
        hadiah.setText(load);
        kalah.setText(load);

        DashboardModel dashboardModel = new DashboardModel(
                username,
                api_token,
                kode_asset
        );

        getDashboard(dashboardModel);
    }

    private void getDashboard(DashboardModel dashboardModel) {
        DashboardUser dashboardUser = RetrofitBuilderGenerator.createService(DashboardUser.class);
        Call<DashboardModel> dashboardModelCall1 = dashboardUser.dashboardModel(dashboardModel);

        dashboardModelCall1.enqueue(new Callback<DashboardModel>() {
            @Override
            public void onResponse(Call<DashboardModel> call, Response<DashboardModel> response) {
                if(response.code() == 200){
                    dimainkan.setText("Dimainkan "+response.body().getData().getPlayed());
                    dispin.setText("Spin "+response.body().getData().getSpin());
                    kustomer.setText("Kustomer "+response.body().getData().getUniq());
                    menang.setText("Menang "+response.body().getData().getWin());
                    hadiah.setText("Hadiah "+response.body().getData().getPrize());
                    kalah.setText("Kalah "+response.body().getData().getLost());
                }
            }

            @Override
            public void onFailure(Call<DashboardModel> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearLoginSession() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    private void logUserOut(){
        clearLoginSession();

        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
        startActivity(intent);

        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.play:
                //play
                //Intent intent = new Intent(DashboardActivity.this, AddSalesActivity.class);
                //startActivity(intent);

                //cek apakah setting pada outlet sudah ada
                PrizeModel prizeModel = new PrizeModel(username, api_token, kode_asset);
                GetPrize getPrize = RetrofitBuilderGenerator.createService(GetPrize.class);
                Call<PrizeModel> prizeModelCall = getPrize.prizeModel(prizeModel);

                prizeModelCall.enqueue(new Callback<PrizeModel>() {
                    @Override
                    public void onResponse(Call<PrizeModel> call, Response<PrizeModel> response) {
                        Log.d("RESPONSE 200", String.valueOf(response));

                        if(response.code() == 200 && response.body().getStatus()){
                            //play
                            Intent intent = new Intent(DashboardActivity.this, AddSalesActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(DashboardActivity.this, "Hadiah belum di set untuk toko anda atau stok hadiah habis", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PrizeModel> call, Throwable t) {
                        Log.d("RESPONSE FAIL", t.getMessage());

                        Toast.makeText(DashboardActivity.this, "Hadiah belum di set untuk toko anda atau stok hadiah habis", Toast.LENGTH_SHORT).show();
                    }
                });

                break;

            case R.id.logout:
                //logout
                logUserOut();
                break;

            case R.id.ads_close_icon:
                //close ads
                adsContainer.setVisibility(View.GONE);
                break;
        }
    }

    public void onDestroy(){
        super.onDestroy();

        clearLoginSession();
    }
}
