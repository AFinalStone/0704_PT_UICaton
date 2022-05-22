package com.example.demo.listener;

import android.os.Bundle;
import android.text.Editable;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.widget.R;
import com.example.widget.databinding.ActivityListenerBinding;

public class ListenerActivity extends AppCompatActivity {

    UserInfo userInfo;
    YouPresenter youPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listener);
        ActivityListenerBinding activityListenerBinding = DataBindingUtil.setContentView(this, R.layout.activity_listener);
        userInfo = new UserInfo();
        activityListenerBinding.setUserInfo(userInfo);
        youPresenter = new YouPresenter();
        activityListenerBinding.setYouPresenter(youPresenter);
    }

    public class YouPresenter {

        public void onNameClick(String name) {
            Toast.makeText(ListenerActivity.this, "用户名：" + name, Toast.LENGTH_SHORT).show();
            userInfo.name.set("你的名字");
        }

        public void afterNameChanged(Editable s) {
            userInfo.name.set(s.toString());
        }

        public void afterPasswordChanged(Editable s) {
            userInfo.password.set(s.toString());
        }

    }

}

