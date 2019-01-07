package com.vjtechsolution.aiceluckywheel;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUserActivity extends AppCompatActivity {

    private EditText username, password, namaOutlet;
    private Double lat, lng;
    private Button daftarButton;

    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        username = findViewById(R.id.daftarUsername);
        password = findViewById(R.id.daftarPassword);
        namaOutlet = findViewById(R.id.daftarNamaOutlet);
        daftarButton = findViewById(R.id.daftarButton);

        daftarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(username.getText().toString().equals("") || password.getText().toString().equals("") || namaOutlet.getText().toString().equals("")){
                    Toast.makeText(RegisterUserActivity.this, "Data login dan outlet harus di isi", Toast.LENGTH_SHORT).show();
                }else{
                    //show loading dialog
                    pDialog = new SweetAlertDialog(RegisterUserActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorAccent));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    final Timer timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {

                            //get user location
                            SmartLocation.with(RegisterUserActivity.this).location()
                                    .oneFix()
                                    .start(new OnLocationUpdatedListener() {
                                        @Override
                                        public void onLocationUpdated(Location location) {
                                            SmartLocation.with(RegisterUserActivity.this).location()
                                                    .oneFix()
                                                    .start(new OnLocationUpdatedListener() {
                                                        @Override
                                                        public void onLocationUpdated(Location location) {
                                                            lat = location.getLatitude();
                                                            lng = location.getLongitude();
                                                        }
                                                    });
                                        }
                                    });

                            if(lat != null && lng != null){
                                timer.cancel();

                                //prepare post parameter
                                RegisterUser registerUser = new RegisterUser(
                                        username.getText().toString(),
                                        password.getText().toString(),
                                        namaOutlet.getText().toString(),
                                        lat,
                                        lng
                                );

                                postRegister(registerUser);
                            }
                        }
                    },0,1000);
                }

            }
        });
    }

    private void postRegister(final RegisterUser registerUser) {
        PostDaftarUser postDaftarUser = RetrofitBuilderGenerator.createService(PostDaftarUser.class);
        Call<RegisterUser> registerUserCall = postDaftarUser.registerUser(registerUser);

        registerUserCall.enqueue(new Callback<RegisterUser>() {
            @Override
            public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {

                if(response.code() == 200){
                    if(response.body().getStatus()){
                        pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        pDialog.setTitleText("Sukses");
                        pDialog.setContentText(response.body().getMessage());
                        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                pDialog.dismissWithAnimation();

                                finish();
                            }
                        });
                    }else{
                        pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        pDialog.setTitleText("Gagal");
                        pDialog.setContentText(response.body().getMessage());
                    }
                }else{
                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Gagal");
                    pDialog.setContentText("Terjadi kesalahan atau server sedang mengalami gangguan");
                }
            }

            @Override
            public void onFailure(Call<RegisterUser> call, Throwable t) {
                pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                pDialog.setContentText("Cek kembali data yang di input");
            }
        });
    }
}
