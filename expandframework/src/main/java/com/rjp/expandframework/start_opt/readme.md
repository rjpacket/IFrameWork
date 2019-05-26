### 一、 优化开机黑屏问题

    1.1 为开屏页增加背景透明样式

```xml
    <style name="AppTheme.Start" parent="Theme.AppCompat.NoActionBar">
        <item name="android:windowIsTranslucent">true</item>
        <item name="android:windowDisablePreview">true</item>
    </style>
```

    1.2 Application会执行onCreate两次

发现执行了两遍 Multidex.install(this); 方法，耗时x2。

    1.3 分别打印Application各初始化执行时间

        1. 初始化BrowserFree
        2. 添加各组件
        3. 初始化Activity的回调
        4. 初始化Bugly
        5. 初始化极光推送
        6. 初始化微信
        7. 初始化Flurry
 
打印耗时(各打印三次，查看平均耗时)
 
        1. BrowserFree -> 耗时60ms
        2. 添加组件 -> 耗时5ms
        3. activity回调 -> 耗时0ms
        4. 初始化Bugly -> 耗时120ms
        5. 初始化极光推送 -> 耗时220ms
        6. 初始化微信 -> 耗时10ms
        7. 初始化Flurry -> 耗时30ms
    
可以看出主要耗时是bugly和极光推送的初始化，但是这两个初始化是可以滞后的。结合开屏页面延迟了1000ms打开首页的空耗时间，所以可以结合使用开屏页面。
但是为了可以尽快的打开开屏页面，所以放在StartActivity的onCreate()明显不合适，所以选择放在StartActivity的onResume()下。因为这个时候用户已经可以看到界面了，但是由于需要等待1000ms，所以就算主线程耗时了500+ms，用户也是无感知的。 
 
### 二、 优化 RecyclerView
    
    2.1 管理RecyclerView的ItemType
    2.2 缓存Item的View，减少findViewById的次数
    2.3 修改数据去刷新页面显示而不是直接操作View
    
### 三、 优化HomeActivity

    3.1 加载时隐藏的布局利用Stub延迟载入 优化时间 -150ms
    3.2 延迟一个极短时间加载布局  - 300ms
    
### 四、 





