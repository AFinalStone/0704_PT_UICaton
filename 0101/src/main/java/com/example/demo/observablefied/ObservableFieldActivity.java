package com.example.demo.observablefied;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.widget.R;
import com.example.widget.databinding.ObservableFieldActivityBinding;

public class ObservableFieldActivity extends AppCompatActivity {


    ObservableActivityInfo observableActivityInfo;
    ObservableActivityInfoHandler observableActivityInfoHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observable_field);
        ObservableFieldActivityBinding observableActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_observable_field);
        observableActivityInfo = new ObservableActivityInfo();
        observableActivityInfo.list.add("张三");
        observableActivityInfo.list.add("李四");
        observableActivityInfo.list.add("王五");
        observableActivityInfo.map.put("2020", 100);
        observableActivityInfo.map.put("2021", 101);
        observableActivityInfo.map.put("2022", 102);
        observableActivityInfoHandler = new ObservableActivityInfoHandler();
        observableActivityBinding.setObservableActivityInfo(observableActivityInfo);
        observableActivityBinding.setObservableActivityInfoHandler(observableActivityInfoHandler);

    }


    public class ObservableActivityInfoHandler {

        public void changeProperty() {
            Log.d("ObservableActivityInfo", "changeProperty方法被执行");
            observableActivityInfo.name.set("更新UI属性");
            observableActivityInfo.activityName.set("更新UI属性");
            observableActivityInfo.fullActivityName.set("更新UI属性");
            observableActivityInfo.index.set(1);
            observableActivityInfo.key.set("2021");
        }
    }

}
