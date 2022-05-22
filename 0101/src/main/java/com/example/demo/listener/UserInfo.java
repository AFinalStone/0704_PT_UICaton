package com.example.demo.listener;

import androidx.databinding.ObservableField;

public class UserInfo {
    public final ObservableField<String> name = new ObservableField<>("你的名字");
    public final ObservableField<String> password = new ObservableField<>("你的密码");
}
