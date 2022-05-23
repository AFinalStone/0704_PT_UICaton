package com.example.demo.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.uicaton.R;
import com.example.uicaton.databinding.ActivityMainBindTest;

public class MainActivity extends AppCompatActivity {

    ActivityInfoHandler activityInfoHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置页面
        ActivityMainBindTest activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityInfoHandler = new ActivityInfoHandler();
        activityMainBinding.setActivityInfoHandler(activityInfoHandler);

    }

    public class ActivityInfoHandler {

        public void testBlockCanary() {
            SystemClock.sleep(5000);
            Log.d("MainActivity===", "testBlockCanary");
        }

        public void toAnimActivity() {
            Log.d("MainActivity===", "toAnimActivity");
            startActivity(new Intent(MainActivity.this, MATActivity.class));
        }

    }

}
