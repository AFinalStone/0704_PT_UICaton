package com.example.demo.main;

import android.app.Application;

import com.example.demo.util.ChoreographerHelper;
import com.example.demo.util.MBlockCanary;

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MBlockCanary.install();
        ChoreographerHelper.start();
//        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }
}