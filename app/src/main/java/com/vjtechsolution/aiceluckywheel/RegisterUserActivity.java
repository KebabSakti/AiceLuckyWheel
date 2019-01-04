package com.vjtechsolution.aiceluckywheel;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        daftarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show loading dialog
                pDialog = new SweetAlertDialog(RegisterUserActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorAccent));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();

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
        });
    }

    private void postRegister(RegisterUser registerUser) {
        PostDaftarUser postDaftarUser = RetrofitBuilderGenerator.createService(PostDaftarUser.class);
        Call<RegisterUser> registerUserCall = postDaftarUser.registerUser(registerUser);

        registerUserCall.enqueue(new Callback<RegisterUser>() {
            @Override
            public void onResponse(Call<RegisterUser> call, Response<RegisterUser> response) {

                if(response.body().getStatus()){
                    pDialog.hide();
                    
                    pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    pDialog.setTitleText("Sukses");
                    pDialog.setContentText("Data berhasil tersimpan");
                    pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            pDialog.hide();

                            finish();
                        }
                    });
                    pDialog.show();
                }else{
                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Gagal");
                    pDialog.setContentText(response.body().getMessage());
                    pDialog.show();
                }

                Log.d("REGISTER.USER", String.valueOf(String.valueOf(response.body().getMessage())));
            }

            @Override
            public void onFailure(Call<RegisterUser> call, Throwable t) {
                pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                pDialog.setContentText("Cek kembali data yang di input");
                //pDialog.show();
            }
        });
    }
}
