package com.vjtechsolution.aiceluckywheel;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.arasthel.asyncjob.AsyncJob;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CameraActivity extends AppCompatActivity {

    private CameraView cameraView;
    private ImageView cameraTrigger;
    private SweetAlertDialog pDialog;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        cameraView = findViewById(R.id.camera);
        cameraView.setLifecycleOwner(this);

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorAccent));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

        cameraTrigger = findViewById(R.id.camTrigger);

        //take pic
        cameraTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog.show();

                cameraView.captureSnapshot();
            }
        });

        //process taken pic
        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] jpeg) {
                super.onPictureTaken(jpeg);

                CameraUtils.decodeBitmap(jpeg, new CameraUtils.BitmapCallback() {
                    @Override
                    public void onBitmapReady(final Bitmap bitmap) {

                        AsyncJob.doInBackground(new AsyncJob.OnBackgroundJob() {
                            @Override
                            public void doOnBackground() {
                                try {

                                    String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();
                                    String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.ENGLISH).format(new Date());
                                    String fileName = timeStamp;

                                    OutputStream out = null;
                                    File file = new File(path, fileName + ".jpg");
                                    out = new FileOutputStream(file);

                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                                    /*
                                    new Resizer(CameraViewActivity.this)
                                            .setTargetLength(800)
                                            .setQuality(80)
                                            .setSourceImage(file)
                                            .getResizedFile();
                                            */

                                    image = file.getAbsolutePath();

                                    out.close();

                                    // Send the result to the UI thread and show it on a Toast
                                    AsyncJob.doOnMainThread(new AsyncJob.OnMainThreadJob() {
                                        @Override
                                        public void doInUIThread() {
                                            pDialog.dismissWithAnimation();

                                            Intent intent = new Intent(CameraActivity.this, AddSalesActivity.class);
                                            intent.putExtra("image", image);
                                            setResult(Activity.RESULT_OK, intent);
                                            finish();
                                        }
                                    });

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                });
            }
        });
    }
}
