package com.example.demo.util;

import android.os.Build;
import android.util.Log;
import android.view.Choreographer;

public class ChoreographerHelper {

    public static void start() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
                long lastFrameTimeNanos = 0;

                @Override
                public void doFrame(long frameTimeNanos) {
                    //上次回调时间
                    if (lastFrameTimeNanos == 0) {
                        lastFrameTimeNanos = frameTimeNanos;
                        Choreographer.getInstance().postFrameCallback(this);
                        return;
                    }
                    long diff = (frameTimeNanos - lastFrameTimeNanos) / 1_000_000;
                    if (diff > 16.6f) {
                        //掉帧数
                        int droppedCount = (int) (diff / 16.6);
                        if (droppedCount > 2) {
                            Log.d("ChoreographerHelper", "UI线程超时(超过16ms)，当前：" + diff + "ms" + ",丢帧：" + droppedCount);
                        }
                    }
                    lastFrameTimeNanos = frameTimeNanos;
                    Choreographer.getInstance().postFrameCallback(this);
                }
            });
        }
    }
}