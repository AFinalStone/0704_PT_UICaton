package com.example.demo.observablefied;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableArrayMap;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;
import androidx.databinding.ObservableMap;

public class ObservableActivityInfo {

    public final ObservableField<String> name = new ObservableField<>("Observable页面");
    public final ObservableField<String> activityName = new ObservableField<>("Observable页面名称");
    public final ObservableField<String> fullActivityName = new ObservableField<>("Observable页面名称全路径");
    public final ObservableInt index = new ObservableInt(0);
    public final ObservableList<String> list = new ObservableArrayList<>();
    public final ObservableField<String> key = new ObservableField<>("2020");
    public final ObservableMap<String, Integer> map = new ObservableArrayMap<>();
}
