package com.vjtechsolution.aiceluckywheel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.pegang);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAuth userAuth = new UserAuth(
                        "famoost",
                        "buyung"
                );

                doLogin(userAuth);
            }
        });
    }

    private void doLogin(UserAuth userAuth) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.2.242:8000")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        PostLogin login = retrofit.create(PostLogin.class);
        Call<UserAuth> userAuthCall = login.loginUser(userAuth);

        userAuthCall.enqueue(new Callback<UserAuth>() {
            @Override
            public void onResponse(Call<UserAuth> call, Response<UserAuth> response) {
                Log.d("DEBUG RESPONSE", "Your Username : " + response.body().getUsername() + "Token : " + response.body().getApi_token());
            }

            @Override
            public void onFailure(Call<UserAuth> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
