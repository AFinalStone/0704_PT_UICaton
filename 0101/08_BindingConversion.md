## BindingConversion

DataBinding 还支持对数据进行转换，或者进行类型转换

与 BindingAdapter 类似，以下方法会将布局文件中所有以@{String}方式引用到的String类型变量加上后缀-conversionString

```c
    @BindingConversion
    public static String conversionString(String text) {
        return text + "-conversionString";
    }
```

Xml 文件

```xml

<TextView android:layout_width="match_parent" android:layout_height="wrap_content"
    android:text='@{"xxx"}' android:textAllCaps="false" />

```

![](pic/img_03.png)

可以看到，对于 Button 来说，BindingAdapter 和 BindingConversion 同时生效了，而 BindingConversion 的优先级要高些
此外，BindingConversion 也可以用于转换属性值的类型 看以下布局，此处在向 background 和 textColor
两个属性赋值时，直接就使用了字符串，按正常情况来说这自然是会报错的，但有了 BindingConversion 后就可以自动将字符串类型的值转为需要的 Drawable 和 Color 了

```xml

<TextView android:layout_width="match_parent" android:layout_height="wrap_content"
    android:background='@{"红色"}' android:padding="20dp" android:text="红色背景蓝色字"
    android:textColor='@{"蓝色"}' />

<TextView android:layout_width="match_parent" android:layout_height="wrap_content"
android:layout_marginTop="20dp" android:background='@{"蓝色"}' android:padding="20dp"
android:text="蓝色背景红色字" android:textColor='@{"红色"}' />

```

```c
	@BindingConversion
    public static Drawable convertStringToDrawable(String str) {
        if (str.equals("红色")) {
            return new ColorDrawable(Color.parseColor("#FF4081"));
        }
        if (str.equals("蓝色")) {
            return new ColorDrawable(Color.parseColor("#3F51B5"));
        }
        return new ColorDrawable(Color.parseColor("#344567"));
    }

    @BindingConversion
    public static int convertStringToColor(String str) {
        if (str.equals("红色")) {
            return Color.parseColor("#FF4081");
        }
        if (str.equals("蓝色")) {
            return Color.parseColor("#3F51B5");
        }
        return Color.parseColor("#344567");
    }

```