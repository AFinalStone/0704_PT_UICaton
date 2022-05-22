[Android DataBinding 从入门到进阶](https://juejin.cn/post/6844903609079971854)

## 一、事件绑定

严格意义上来说，事件绑定也是一种变量绑定，只不过设置的变量是回调接口而已 事件绑定可用于以下多种回调事件

android:onClick android:onLongClick android:afterTextChanged android:onTextChanged ...

在 Activity 内部新建一个 YouPresenter 类来声明 onClick() 和 afterTextChanged() 事件相应的回调方法

布局文件：

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

        <import type="com.example.demo.listener.UserInfo" />

        <import type="com.example.demo.listener.ListenerActivity.YouPresenter" />

        <variable name="userInfo" type="UserInfo" />

        <variable name="youPresenter" type="YouPresenter" />

    </data>


    <LinearLayout android:layout_width="match_parent" android:layout_height="match_parent"
        android:orientation="vertical" android:padding="10dp"
        tools:context="com.example.demo.main.MainActivity">

        <LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content"
            android:background="#FF6000" android:gravity="center" android:orientation="vertical"
            android:padding="10dp">


            <EditText android:layout_width="wrap_content" android:layout_height="wrap_content"
                android:afterTextChanged="@{youPresenter.afterNameChanged}" android:gravity="center"
                android:minWidth="100dp" android:text="@={userInfo.name}"
                android:textColor="@color/black" />

            <EditText android:layout_width="wrap_content" android:layout_height="wrap_content"
                android:afterTextChanged="@{youPresenter.afterPasswordChanged}"
                android:gravity="center" android:minWidth="100dp"
                android:text="@={userInfo.password}" android:textColor="@color/black" />

            <TextView android:layout_width="match_parent" android:layout_height="wrap_content"
                android:gravity="center" android:text="@{userInfo.name+userInfo.password}" />

            <Button android:layout_width="wrap_content" android:layout_height="wrap_content"
                android:gravity="center" android:minWidth="100dp"
                android:onClick="@{()->youPresenter.onNameClick(userInfo.name)}"
                android:text="重置用户名" android:textColor="@color/black" />

        </LinearLayout>

    </LinearLayout>
</layout>
```

代码文件：

```java
package com.example.demo.listener;

import androidx.databinding.ObservableField;

public class UserInfo {
    public final ObservableField<String> name = new ObservableField<>("你的名字");
    public final ObservableField<String> password = new ObservableField<>("你的密码");
}

```

```java
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


```

## 二、使用类方法

首先定义一个静态方法

