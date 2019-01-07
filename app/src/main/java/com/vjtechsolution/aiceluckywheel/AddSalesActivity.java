package com.vjtechsolution.aiceluckywheel;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddSalesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sales);

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new CustomPagerAdapter(this));
    }
}
