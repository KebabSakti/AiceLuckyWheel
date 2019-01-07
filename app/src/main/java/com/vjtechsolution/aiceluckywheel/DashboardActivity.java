package com.vjtechsolution.aiceluckywheel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView dimainkan, dispin, kustomer, menang, hadiah, kalah;
    private ImageView play;
    private Button logout;

    private String username, api_token, kode_asset;

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

        play.setOnClickListener(this);
        logout.setOnClickListener(this);

        getDashboardData();
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
                Intent intent = new Intent(DashboardActivity.this, AddSalesActivity.class);
                startActivity(intent);
                break;

            case R.id.logout:
                //logout
                logUserOut();
                break;

        }
    }

    public void onDestroy(){
        super.onDestroy();

        clearLoginSession();
    }
}
