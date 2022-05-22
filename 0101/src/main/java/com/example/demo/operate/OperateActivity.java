package com.example.demo.statictest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.demo.listener.UserInfo;
import com.example.widget.R;
import com.example.widget.databinding.ActivityListenerBinding;

public class OperateActivity extends AppCompatActivity {

    UserInfo userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operate);
        ActivityListenerBinding activityListenerBinding = DataBindingUtil.setContentView(this, R.layout.activity_operate);
        userInfo = new UserInfo();
        activityListenerBinding.setUserInfo(userInfo);
    }


}

