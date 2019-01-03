package com.vjtechsolution.aiceluckywheel;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.newtronlabs.easypermissions.EasyPermissions;
import com.newtronlabs.easypermissions.listener.IError;
import com.newtronlabs.easypermissions.listener.IPermissionsListener;

import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements IPermissionsListener, View.OnClickListener {

    private EditText username;
    private EditText password;
    private Button loginBtn;
    private TextView registerBtn;

    private Boolean internet;
    private Boolean location;
    private Boolean camera;

    SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.loginUsername);
        password = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginButton);
        registerBtn = findViewById(R.id.loginRegister);

        internet = (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED);
        location = (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        camera = (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);

        EasyPermissions.getInstance().requestPermissions(this, this);

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                //progress dialog
                pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorAccent));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();

                //disable form login jika izin tidak diberikan
                if(!internet || !location || !camera){
                    Toast.makeText(MainActivity.this, "Aplikasi memerlukan izin lokasi dan kamera untuk berfungsi", Toast.LENGTH_LONG).show();
                }else{
                    UserAuth userAuth = new UserAuth(
                            username.getText().toString(),
                            password.getText().toString()
                    );

                    doLogin(userAuth);
                }
                break;

            case R.id.loginRegister:
                Intent register = new Intent(MainActivity.this, RegisterUserActivity.class);
                startActivity(register);
                break;
        }
    }

    private void doLogin(UserAuth userAuth) {

        PostLogin login = RetrofitBuilderGenerator.createService(PostLogin.class);
        Call<UserAuth> userAuthCall = login.loginUser(userAuth);

        userAuthCall.enqueue(new Callback<UserAuth>() {
            @Override
            public void onResponse(Call<UserAuth> call, Response<UserAuth> response) {

                Log.d("DEBUG RES", String.valueOf(response.body().getStatus())+' '+response.body().getData().getUsername());
                pDialog.hide();

                /*
                if(response.body().getStatus()){
                    //login
                    Toast.makeText(MainActivity.this, "Okeee", Toast.LENGTH_SHORT).show();
                }else{
                    //show error msg
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                */
            }

            @Override
            public void onFailure(Call<UserAuth> call, Throwable t) {
                pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                pDialog.setTitleText("Login Gagal");
                pDialog.setContentText(t.getMessage());
                pDialog.show();
            }
        });
    }

    @Override
    public void onRequestSent(Set<String> set) {

    }

    @Override
    public void onFailure(IError iError) {

    }

    @Override
    public void onCompleted(Set<String> set, Set<String> set1) {

    }
}
