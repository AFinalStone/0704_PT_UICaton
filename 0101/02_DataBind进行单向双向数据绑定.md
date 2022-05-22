[Android DataBinding 从入门到进阶](https://juejin.cn/post/6844903609079971854)

## 一、单向数据绑定

一个纯净的 ViewModel 类被更新后，并不会让 UI 自动更新。  
而数据绑定后，我们自然会希望数据变更后 UI 会即时刷新，Observable 就是为此而生的概念

### 1.1 让bean对象继承BaseObservable

BaseObservable 提供了 notifyChange() 和 notifyPropertyChanged() 两个方法，   
前者会刷新所有的值域，后者则只更新对应 BR 的 flag，该 BR  
的生成通过注释 @Bindable 生成， 可以通过 BR notify 特定属性关联的视图

- 为setName()添加notifyChange方法，当该方法被调用，bean对象的所有属性都会被更新到UI上
- 为activityName属性添加@Bindable注解，为setActivityName()添加 notifyPropertyChanged(BR.activityName)
  ,当该方法被调用，只有该属性会被更新到UI上

```java
package com.example.demo.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class ActivityInfo extends BaseObservable {
    private String name;
    private String activityName;
    private String fullActivityName;

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

    @Bindable//只更新activityName属性对应的UI
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getFullActivityName() {
        return fullActivityName;
    }

    public void setFullActivityName(String fullActivityName) {
        this.fullActivityName = fullActivityName;
    }
}

```

```java
    public class ActivityInfoHandler {

    public void changeOnlyProperty() {
        Log.d("ActivityInfoHandler", "changeOnlyProperty方法被执行");
        activityInfo.setFullActivityName("更新单一属性");
        activityInfo.setActivityName("更新单一属性");
    }

    public void changeAllProperty() {
        Log.d("ActivityInfoHandler", "changeAllProperty方法被执行");
        activityInfo.setFullActivityName("更新全部属性");
        activityInfo.setName("更新全部属性");
    }

}
```

### 注册监听器

实现了 Observable 接口的类允许注册一个监听器，当可观察对象的属性更改时就会通知这个监听器，此时就需要用到 OnPropertyChangedCallback

当中 propertyId 就用于标识特定的字段

```cmd
        activityInfo.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Log.d("MainActivity", "propertyId = " + propertyId);
                if (propertyId == BR.activityName) {
                    Log.d("MainActivity", "activityInfo.activityName.propertyId = " + propertyId);
                }
            }
        });
```

分别点击修改单一属性和修改全部属性按钮，日志信息如下：

```cmd
2022-05-15 22:06:29.287 11623-11623/com.example.widget D/MainActivity: propertyId = 1
2022-05-15 22:06:29.287 11623-11623/com.example.widget D/MainActivity: activityInfo.activityName.propertyId = 1
2022-05-15 22:06:32.205 11623-11623/com.example.widget D/MainActivity: propertyId = 0
```

### 1.3 ObservableField变量

- 继承于 Observable 类相对来说限制有点高，且也需要进行 notify 操作，因此为了简单起见可以选择使用 ObservableField。ObservableField 可以理解为官方对
  BaseObservable 中字段的注解和刷新等操作的封装，官方原生提供了对基本数据类型的封装，例如
  ObservableBoolean、ObservableByte、ObservableChar、ObservableShort、ObservableInt、ObservableLong、ObservableFloat、ObservableDouble
  以及 ObservableParcelable ，也可通过 ObservableField 泛型来申明其他类型
- DataBinding 也提供了包装类用于替代原生的 List 和 Map，分别是 ObservableList 和
  ObservableMap,当其包含的数据发生变化时，绑定的视图也会随之进行刷新

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <data class="ObservableFieldActivityBinding">

        <import type="com.example.demo.observablefied.ObservableActivityInfo" />

        <import
            type="com.example.demo.observablefied.ObservableFieldActivity.ObservableActivityInfoHandler" />

        <variable name="observableActivityInfo"
            type="com.example.demo.observablefied.ObservableActivityInfo" />

        <variable name="observableActivityInfoHandler" type="ObservableActivityInfoHandler" />

    </data>


    <LinearLayout android:layout_width="match_parent" android:layout_height="match_parent"
        android:orientation="vertical" android:padding="10dp"
        tools:context="com.example.demo.main.MainActivity">


        <LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content"
            android:layout_marginTop="20dp" android:background="#FF6000" android:gravity="center"
            android:orientation="vertical" android:padding="10dp">

            <TextView android:id="@+id/tv_observable_name" android:layout_width="wrap_content"
                android:layout_height="wrap_content" android:gravity="center"
                android:minWidth="100dp" android:text="@{observableActivityInfo.name}"
                android:textColor="@color/black" />

            <TextView android:id="@+id/tv_observable_activity_name"
                android:layout_width="wrap_content" android:layout_height="wrap_content"
                android:gravity="center" android:minWidth="100dp"
                android:text="@{observableActivityInfo.activityName}"
                android:textColor="@color/black" />

            <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
                android:gravity="center" android:minWidth="100dp"
                android:text="@{observableActivityInfo.fullActivityName}"
                android:textColor="@color/black" />

            <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
                android:gravity="center" android:minWidth="100dp"
                android:text="@{String.valueOf(observableActivityInfo.index)}"
                android:textColor="@color/black" />

            <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
                android:gravity="center" android:minWidth="100dp"
                android:text="@{observableActivityInfo.list.get(observableActivityInfo.index)}"
                android:textColor="@color/black" />

            <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
                android:gravity="center" android:minWidth="100dp"
                android:text="@{String.valueOf(observableActivityInfo.map.get(observableActivityInfo.key))}"
                android:textColor="@color/black" />

            <Button android:layout_width="match_parent" android:layout_height="wrap_content"
                android:onClick="@{()->observableActivityInfoHandler.changeProperty()}"
                android:text="更新属性" android:textAllCaps="false" />

        </LinearLayout>
    </LinearLayout>
</layout>
```

```java
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

```

```java
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


```

### ObservableCollection

## 二、双向数据绑定

双向绑定的意思即为当数据改变时同时使视图刷新，而视图改变时也可以同时改变数据 看以下例子，当 EditText 的输入内容改变时，会同时同步到变量 goods,绑定变量的方式比单向绑定多了一个等号：

```xml

<TextView android:id="@+id/tv_observable_name" android:layout_width="wrap_content"
    android:layout_height="wrap_content" android:gravity="center" android:minWidth="100dp"
    android:text="@{observableActivityInfo.name}" android:textColor="@color/black" />

<EditText android:layout_width="wrap_content" android:layout_height="wrap_content"
android:gravity="center" android:minWidth="100dp" android:text="@={observableActivityInfo.name}"
android:textColor="@color/black" />
```

EditText和TextView的内容会实时进行更新