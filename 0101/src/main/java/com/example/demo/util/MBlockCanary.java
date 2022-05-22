package com.example.demo.util;

import android.os.Looper;

public class MBlockCanary {
    public static void install() {
        LogMonitor logMonitor = new LogMonitor();
        Looper.getMainLooper().setMessageLogging(logMonitor);
    }
}