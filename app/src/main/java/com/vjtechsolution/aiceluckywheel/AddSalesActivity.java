package com.vjtechsolution.aiceluckywheel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSalesActivity extends AppCompatActivity {

    private Button next, prev;
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;

    private HashMap<String, Integer> custProduct = new HashMap<>();
    private String foto = "";
    private String nama = "";
    private String no_telp = "";
    private Integer sumProd = 0;
    private String inNoTlp;

    private TextView toolbarHeading;
    private Integer page = 0;

    private String username, api_token, kode_asset;
    private SharedPreferences sharedPreferences;

    private SweetAlertDialog pDialog;

    private String session;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sales);

        sharedPreferences = getSharedPreferences(getString(R.string.key_preference), Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username","");
        api_token = sharedPreferences.getString("api_token", "");
        kode_asset = sharedPreferences.getString("kode_asset", "");

        android.support.v7.widget.Toolbar mActionBarToolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //progress dialog
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorAccent));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);

        session = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.ENGLISH).format(new Date());
        toolbarHeading = findViewById(R.id.toolbarSalesHeading);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sumProd < 1){
                    Toast.makeText(AddSalesActivity.this, "Masukkan detail pembelian kustomer", Toast.LENGTH_SHORT).show();
                }else{
                    if(page != 1){
                        next.setText("Mulai Main");

                        page += 1;
                        viewPager.setCurrentItem(page, true);
                    }else{
                        if(foto.equals("") || nama.equals("") || no_telp.equals("")){
                            Toast.makeText(AddSalesActivity.this, "Isi field kosong dan centang checkbox", Toast.LENGTH_SHORT).show();
                        }else{

                            ArrayList<String> products = new ArrayList<>();
                            ArrayList<Integer> qtyProducts = new ArrayList<>();

                            for(Map.Entry<String, Integer> pair : custProduct.entrySet()){
                                products.add(pair.getKey());
                                qtyProducts.add(pair.getValue());
                            }

                            postData(username, api_token, kode_asset, foto, nama, no_telp, products, qtyProducts);

                        }
                    }

                    setToolbarHeading(page);
                }

            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setText("Next");

                page = (page == 0) ? page : page-1;
                viewPager.setCurrentItem(page, true);

                setToolbarHeading(page);
            }
        });

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

    }

    private void postData(String username, String api_token, String kode_asset, String foto, String nama, String no_telp, ArrayList<String> products, ArrayList<Integer> qtyProducts) {
        pDialog.show();
        inNoTlp = no_telp;

        PostSalesData post = RetrofitBuilderGenerator.createService(PostSalesData.class);

        File file = new File(foto);
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("foto", file.getName(), mFile);
        RequestBody uname = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), api_token);
        RequestBody asset = RequestBody.create(MediaType.parse("text/plain"), kode_asset);
        RequestBody namao = RequestBody.create(MediaType.parse("text/plain"), nama);
        RequestBody no = RequestBody.create(MediaType.parse("text/plain"), no_telp);
        RequestBody ss = RequestBody.create(MediaType.parse("text/plain"), session);
        Call<SalesData> salesDataCallback = post.postIt(uname, token, asset, fileToUpload, namao, no, ss, products, qtyProducts);

        salesDataCallback.enqueue(new Callback<SalesData>() {
            @Override
            public void onResponse(Call<SalesData> call, Response<SalesData> response) {
                //Log.d("RESDATA", String.valueOf(response.body().getMessage()));

                if(response.code() == 200){
                    Intent intent = new Intent(AddSalesActivity.this, GameActivity.class);
                    intent.putExtra("session", session);
                    intent.putExtra("total", sumProd);
                    intent.putExtra("no_telp", inNoTlp);
                    startActivity(intent);

                    pDialog.dismissWithAnimation();

                    finish();
                }else{
                    pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Gagal");
                    pDialog.setContentText(response.message());
                }

            }

            @Override
            public void onFailure(Call<SalesData> call, Throwable t) {
                Toast.makeText(AddSalesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismissWithAnimation();
            }
        });
    }

    private void setToolbarHeading(int currentItem) {

        //set toolbar text
        String heading ="";

        switch (currentItem){
            case 0:
                heading = "Pilih Produk";
                break;

            case 1:
                heading = "Lengkapi Data Kustomer";
                break;
        }

        toolbarHeading.setText(heading);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EvBusProduct evBusProduct){
        if(!evBusProduct.getProduct().equals("")){
            sumProd = 0;
            //produk dipilih
            custProduct.put(evBusProduct.getProduct(), evBusProduct.getQty());

            //Log.d("RES", String.valueOf(custProduct));

            for(Integer i : custProduct.values()){
                sumProd += i;
            }

        }else {
            foto = evBusProduct.getFoto();
            nama = evBusProduct.getNama();
            no_telp = evBusProduct.getNo_telp();

            //Log.d("RETTT.PRO", String.valueOf(custProduct) + " Size : " + String.valueOf(sumProd));
            //Log.d("RETTT.KUST", nama + " " + no_telp + " : " + foto);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop(){
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
