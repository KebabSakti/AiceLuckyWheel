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

public class RegisterUserActivity extends AppCompatActivity {

    private EditText username, password, namaOutlet;
    private Double lat, lng;
    private Button daftarButton;

    private SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorAccent));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

        username = findViewById(R.id.daftarUsername);
        password = findViewById(R.id.daftarPassword);
        namaOutlet = findViewById(R.id.daftarNamaOutlet);
        daftarButton = findViewById(R.id.daftarButton);

        daftarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get user location
                SmartLocation.with(RegisterUserActivity.this).location()
                        .oneFix()
                        .start(new OnLocationUpdatedListener() {
                            @Override
                            public void onLocationUpdated(Location location) {
                                SmartLocation.with(this).location()
                                        .oneFix()
                                        .start(new OnLocationUpdatedListener() {
                                            @Override
                                            public void onLocationUpdated(Location location) {
                                                lat = location.getLatitude();
                                                lng = location.getLongitude();
                                            }
                                        });

                                Log.d("SM LOCATION", String.valueOf(lat)+' '+String.valueOf(lng));
                            }
                        });
                //pDialog.show();

                /*
                RegisterUser registerUser = new RegisterUser(

                );
                */
                postRegister();
            }
        });
    }

    private void postRegister() {
        PostDaftarUser postDaftarUser = RetrofitBuilderGenerator.createService(PostDaftarUser.class);
        //Call<RegisterUser> registerUserCall = postDaftarUser.registerUser();
    }
}
