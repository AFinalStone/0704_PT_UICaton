## Include

D对于 include 的布局文件，一样是支持通过 dataBinding 来进行数据绑定，此时一样需要在待 include 的布局中依然使用 layout 标签，声明需要使用到的变量

### View_include.xml

```xml

<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>
        <import type="com.leavesc.databinding_demo.model.User" />
        <variable name="userInfo" type="User" />
    </data>

    <android.support.constraint.ConstraintLayout android:layout_width="match_parent"
        android:layout_height="wrap_content" android:background="#acc">

        <TextView android:layout_width="match_parent" android:layout_height="wrap_content"
            android:gravity="center" android:padding="20dp" android:text="@{userInfo.name}" />

    </android.support.constraint.ConstraintLayout>
</layout>

```

在主布局文件中将相应的变量传递给 include 布局，从而使两个布局文件之间共享同一个变量

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:bind="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <import type="com.leavesc.databinding_demo.model.User" />
        <variable name="userInfo" type="User" />
    </data>

    <LinearLayout android:layout_width="match_parent" android:layout_height="match_parent"
        android:orientation="vertical" tools:context=".Main6Activity">

        <include layout="@layout/view_include" bind:userInfo="@{userInfo}" />

    </LinearLayout>
</layout>


```

### ViewStub

DataBinding 一样支持 ViewStub 布局

在布局文件中引用 viewStub 布局

```xml

<ViewStub android:id="@+id/view_stub" android:layout_width="match_parent"
    android:layout_height="wrap_content" android:layout="@layout/view_stub" />
```

获取到 ViewStub 对象，由此就可以来控制 ViewStub 的可见性

```cmd
    ActivityMain6Binding activityMain6Binding=DataBindingUtil.setContentView(this,R.layout.activity_main6);
    View view=activityMain6Binding.viewStub.getViewStub().inflate();
```

如果需要为 ViewStub 绑定变量值，则 ViewStub 文件一样要使用 layout 标签进行布局，主布局文件使用自定义的 bind 命名空间将变量传递给 ViewStub

```xml

<ViewStub android:id="@+id/view_stub" android:layout_width="match_parent"
    android:layout_height="wrap_content" android:layout="@layout/view_stub"
    bind:userInfo="@{userInfo}" />

```

如果在 xml 中没有使用 bind:userInfo="@{userInf}" 对 ViewStub 进行数据绑定，则可以等到当 ViewStub Inflate 时再绑定变量，此时需要为
ViewStub 设置 setOnInflateListener回调函数，在回调函数中进行数据绑定
