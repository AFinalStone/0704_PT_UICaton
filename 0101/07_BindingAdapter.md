## BindingAdapter

dataBinding 提供了 BindingAdapter 这个注解用于支持自定义属性，或者是修改原有属性。注解值可以是已有的 xml 属性，例如 android:src、android:
text等，也可以自定义属性然后在 xml 中使用 例如，对于一个 ImageView ，我们希望在某个变量值发生变化时，可以动态改变显示的图片，此时就可以通过 BindingAdapter 来实现
需要先定义一个静态方法，为之添加 BindingAdapter 注解，注解值是为 ImageView 控件自定义的属性名，而该静态方法的两个参数可以这样来理解：当 ImageView 控件的 url
属性值发生变化时，dataBinding 就会将 ImageView 实例以及新的 url 值传递给 loadImage() 方法，从而可以在此动态改变 ImageView 的相关属性

```c
    @BindingAdapter({"url"})
    public static void loadImage(ImageView view, String url) {
        Log.e(TAG, "loadImage url : " + url);
    }
```

在 xml 文件中关联变量值，当中，bind 这个名称可以自定义

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:bind="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <import type="com.leavesc.databinding_demo.model.Image" />
        <variable name="image" type="Image" />
    </data>

    <android.support.constraint.ConstraintLayout android:layout_width="match_parent"
        android:layout_height="match_parent" tools:context=".Main8Activity">

        <ImageView android:id="@+id/image" android:layout_width="wrap_content"
            android:layout_height="wrap_content" android:src="@drawable/ic_launcher_background"
            bind:url="@{image.url}" />

    </android.support.constraint.ConstraintLayout>
</layout>

```

BindingAdapter 更为强大的一点是可以覆盖 Android 原先的控件属性。例如，可以设定每一个 Button 的文本都要加上后缀：“-Button”

```c
    @BindingAdapter("android:text")
    public static void setText(Button view, String text) {
        view.setText(text + "-Button");
    }
```

```xml

<Button android:layout_width="match_parent" android:layout_height="wrap_content"
    android:onClick="@{()->handler.onClick(image)}" android:text='@{"改变图片Url"}' />

```

这样，整个工程中使用到了 "android:text" 这个属性的控件，其显示的文本就会多出一个后缀

![](pic/img_02.png)

