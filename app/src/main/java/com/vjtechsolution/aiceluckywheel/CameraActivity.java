package com.vjtechsolution.aiceluckywheel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.otaliastudios.cameraview.CameraView;

public class CameraActivity extends AppCompatActivity {

    private CameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        cameraView = findViewById(R.id.camera);
        cameraView.setLifecycleOwner(this);
    }
}
