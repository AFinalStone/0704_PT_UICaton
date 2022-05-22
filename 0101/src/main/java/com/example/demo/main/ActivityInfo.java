package com.example.demo.main;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.widget.BR;

public class ActivityInfo extends BaseObservable {
    private String name;
    @Bindable    //只更新activityName属性对应的UI
    public String activityName;
    private String fullActivityName;
    private int code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyChange();//只更新ActivityInfo中所有属性对应的UI
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
        //只更新本字段
        notifyPropertyChanged(BR.activityName);
    }

    public String getFullActivityName() {
        return fullActivityName;
    }

    public void setFullActivityName(String fullActivityName) {
        this.fullActivityName = fullActivityName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
