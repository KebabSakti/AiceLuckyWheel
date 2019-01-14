package com.vjtechsolution.aiceluckywheel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

public class AddSalesActivity extends AppCompatActivity {

    private Button next, prev;
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;

    private CustomerSalesDetail customerSalesDetail;
    private HashMap<String, Integer> custProduct = new HashMap<>();

    private TextView toolbarHeading;
    private Integer page = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sales);

        EventBus.getDefault().register(this);

        android.support.v7.widget.Toolbar mActionBarToolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbarHeading = findViewById(R.id.toolbarSalesHeading);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page != 1){
                    next.setText("Mulai Main");

                    page += 1;
                    viewPager.setCurrentItem(page, true);
                }else{
                    Toast.makeText(AddSalesActivity.this, "Mulai maennnn", Toast.LENGTH_SHORT).show();
                }

                setToolbarHeading(page);
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

    private void setToolbarHeading(int currentItem) {

        //set toolbar text
        String heading ="";

        switch (currentItem){
            case 0:
                heading = "Pilih Produk";
                break;

            case 1:
                heading = "Ambil Foto Kustomer";
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
        //Toast.makeText(this, evBusProduct.getProduct()+" "+String.valueOf(evBusProduct.getQty()), Toast.LENGTH_SHORT).show();

        custProduct.put(evBusProduct.getProduct(), evBusProduct.getQty());

        Log.d("RET DATA", String.valueOf(custProduct)+" Size : "+String.valueOf(custProduct.size()));
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
