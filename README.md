# Android通用适配器和常用的工具类
Android通用的Adapter for ListView,GridView,RecyclerView等，支持多种ItemType布局

<br>

[![Bintray][icon_Bintray]][Bintray]
[![GitHub issues][icon_issues]][issues]
[![GitHub forks][icon_forks]][forks]
[![GitHub stars][icon_stars]][stars]

## 目录<a name="目录">
* [使用](#使用)
* [CommonAdapter](#CommonAdapter)
    * [通用适配器](#通用适配器)
    * [多布局通用适配器](#多布局通用适配器)
* [BaseRecyclerAdapter](#BaseRecyclerAdapter)
    * [RecyclerView通用适配器](#RecyclerView通用适配器)
    * [RecyclerView多布局通用适配器](#RecyclerView多布局通用适配器)
* [Utils](#Utils)
    * [权限](#权限)
    * [常用工具类](#常用工具类)
* [Assist](#Assist)
* [版本更新](#版本更新)
* [感谢](#感谢)

<br>

<!-- you should configure jcenter repository-->
## 导入Android Studio<a name="使用">
添加jCenter远程依赖到module里的build.gradle：
```
dependencies {
    compile 'com.excellence:basetools:1.2.2'
    // 或者直接使用最新版本
    // compile 'com.excellence:basetools:+'
}
```
或者直接添加本地Library依赖
```
compile project(':basetoolslibrary')
```

<br>

## CommonAdapter<a name="CommonAdapter">

### gridview，listview的通用适配器<a name="通用适配器">

示例：[GridAdapterActivity][GridAdapterActivity]


```java
// 创建adapter类继承CommonAdapter
private class AppGridAdapter extends CommonAdapter<ResolveInfo>
{
    public AppGridAdapter(Context context, List<ResolveInfo> datas, int layoutId)
    {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder viewHolder, ResolveInfo item, int position)
    {
        ImageView iconView = viewHolder.getView(android.R.id.icon);
        iconView.setImageDrawable(item.loadIcon(mPackageManager));
        viewHolder.setText(android.R.id.text1, item.loadLabel(mPackageManager).toString());
    }
}
```


```java
// ViewHolder辅助方法
public <T extends View> T getView(int viewId);                                    // 用于获取Item内的子控件，参数为控件的id
public ViewHolder setText(int viewId, int strId);                                 // 用于设置文本，参数控件id、字符串id
public ViewHolder setBackgroundResource(int viewId, int resId);                   // 用于设置背景图片，参数控件id、图片id
public ViewHolder setImageResource(int viewId, int resId);                        // 用于设置ImageView图片资源，参数控件id、图片id
public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener);  // 用于点击事件监听
*
*
*
// 可拓展其他方法
```


```java
// 刷新适配器
adapter.notifyNewData(data);
```

### gridview，listview的多布局通用适配器<a name="多布局通用适配器">

示例：[MultiItemAdapterActivity][MultiItemAdapterActivity]

```java
// 多布局适配器
private class ChatAdapter extends MultiItemTypeAdapter<People>
{
    public ChatAdapter(Context context, List<People> messages)
    {
        super(context, messages);
        addItemViewDelegate(new ComputerDelegate());
        addItemViewDelegate(new BlueDelegate());
        addItemViewDelegate(new PurpleDelegate());
    }
}

// 不同的布局视图
private class ComputerDelegate implements ItemViewDelegate<People>
{
    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.item_computer;
    }

    @Override
    public boolean isForViewType(People item, int position)
    {
        return item instanceof ComputerData;
    }

    @Override
    public void convert(ViewHolder viewHolder, People item, int position)
    {
        viewHolder.setText(R.id.computer_text, item.getMsg());
    }
}
```

<br>

## BaseRecyclerAdapter<a name="BaseRecyclerAdapter">

### RecyclerView的通用适配器<a name="RecyclerView通用适配器">

示例：[RecyclerAdapterActivity][RecyclerAdapterActivity]

```java
// 创建adapter类继承BaseRecyclerAdapter
private class AppRecyclerAdapter extends BaseRecyclerAdapter<ResolveInfo>
{
    private PackageManager mPackageManager = null;

    public AppRecyclerAdapter(Context context, List<ResolveInfo> datas, int layoutId)
    {
        super(context, datas, layoutId);
        mPackageManager = context.getPackageManager();
    }

    @Override
    public void convert(RecyclerViewHolder viewHolder, ResolveInfo item, int position)
    {
        viewHolder.setText(android.R.id.text1, item.loadLabel(mPackageManager));
        viewHolder.setImageDrawable(android.R.id.icon, item.loadIcon(mPackageManager));
    }

}
```


```java
// RecyclerViewHolder辅助方法类同ViewHolder辅助方法
*
*
*
```

### RecyclerView的多布局通用适配器<a name="RecyclerView多布局通用适配器">

示例：[MultiItemRecyclerAdapterActivity][MultiItemRecyclerAdapterActivity]

```java
// 多布局适配器
private class WarAdapter extends MultiItemTypeRecyclerAdapter<People>
{
    public WarAdapter(Context context, List<People> datas)
    {
        super(context, datas);
        addItemViewDelegate(new ComputerRecyclerDelegate());
        addItemViewDelegate(new BlueRecyclerDelegate());
        addItemViewDelegate(new PurpleRecyclerDelegate());
    }
}

// 不同的布局视图
private class ComputerRecyclerDelegate implements ItemViewDelegate<People>
{
    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.item_computer;
    }

    @Override
    public boolean isForViewType(People item, int position)
    {
        return item instanceof ComputerData;
    }

    @Override
    public void convert(RecyclerViewHolder viewHolder, People item, int position)
    {
        viewHolder.setText(R.id.computer_text, item.getMsg());
    }
}
```

<br>

## Utils<a name="Utils">

### 权限<a name="权限">
```
<uses-permission android:name="android.permission.GET_TASKS"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
```


### 常用工具类<a name="常用工具类">

> - **Activity相关→[ActivityUtils.java][ActivityUtils]**
```
startAnotherActivity   : Activity跳转
setActivityWindowAlpha : 设置Activity窗口透明值
isActivityTopStack     : 判断Activity是否在栈顶
getLauncherActivity    : 获取某应用入口Activity
```

> - **应用相关→[AppUtils.java][AppUtils]**
```
getInstalledApps       : 获取安装的全部应用
getSystemInstalledApps : 获取安装的系统应用
getUserInstalledApps   : 获取安装的第三方应用
getPermissionList      : 获取某应用的所有权限
checkPermission        : 检测某应用是否有某权限
getAppVersionName      : 获取当前应用版本名
getAppVersionCode      : 获取当前应用版本号
getAppSize             : 获取当前应用大小
getAppTime             : 获取当前应用安装时间
getAppPath             : 获取当前应用路径
getAPKFileSignature    : 获取apk文件的签名
getPackageSignature    : 获取某安装应用的签名
isAppInstalled         : 判断应用是否安装
isAppDebug             : 判断当前应用是否是Debug版本
```

> - **关闭相关→[CloseUtils.java][CloseUtils]**
```
closeIO        : 关闭IO
closeIOQuietly : 安静关闭IO
```

> - **转换相关→[ConvertUtils.java][ConvertUtils]**
```
bytesToHexString          : bytes转16进制
inputStream2OutputStream  : inputStream转outPutStream
inputStream2Bytes         : inputStream转bytes
inputStream2String        : inputStream转字符串
inputStream2StringBuilder : inputStream转StringBuilder
```

> - **存储相关→[DBUtils.java][DBUtils]**
```
init         : 初始化，设置存储文件名
contains     : 判断键值是否存在
setSetting   : 存储配置
getString    : 读取字符串配置
getBoolean   : 读取Boolean配置
getInt       : 读取int配置
getLong      : 读取long配置
getFloat     : 读取float配置
getStringSet : 读取Set<String>配置
remove       : 删除配置
clear        : 清空配置
```

> - **分辨率相关→[DensityUtils.java][DensityUtils]**
```
getDensity      : 获取当前屏幕分辨率
getScaleDensity : 获取当前文字分辨率
dp2px           : dp转px
px2dp           : px转dp
sp2px           : sp转px
px2sp           : px转sp
```

> - **判断空相关→[EmptyUtils.java][EmptyUtils]**
```
isEmpty    : 判断对象是否为空
isNotEmpty : 判断对象是否非空
```

> - **异常相关→[ExceptionUtils.java][ExceptionUtils]**
```
printException : 打印异常信息字符串
```

> - **文件相关→[FileUtils.java][FileUtils]**
```
createNewFile      : 创建文件
deleteFile         : 删除文件
mkDir              : 创建目录
deleteDir          : 删除目录
deletePostfixFiles : 删除目录下的某后缀文件
formatFileSize     : 格式化文件大小
getFileOrDirSize   : 获取目录、文件大小
getFileSize        : 获取文件大小
getDirSize         : 获取目录大小
chmod              : 修改目录、文件权限
isFileExists       : 判断文件或目录是否存在
```

> - **Handler相关→[HandlerUtils.java][HandlerUtils]**
```
HandlerHolder : 使用必读
```

> - **Image相关→[ImageUtils.java][ImageUtils]**
```
drawable2Bitmap : drawable转bitmap
bitmap2Drawable : bitmap转drawable
view2Bitmap     : view转Bitmap
```

> - **网络相关→[NetworkUtils.java][NetworkUtils]**
```
getActiveNetworkInfo : 获取活动的网络信息
isConnected : 检查网络是否连接
isAvailableByPing : 判断网络是否可用
isMobileDataEnabled : 判断移动数据是否打开
setMobileDataEnabled : 打开或关闭移动数据（舍弃不可用）
is4G : 判断是否是4G网络
isWiFiEnabled : 判断是否打开WiFi
setWiFiEnabled : 打开或关闭WiFi
isWiFiConnected : 判断WiFi是否连接
isWiFiAvailable : 判断WiFi是否可用
getNetworkOperatorName : 获取网络运营商名称
getNetworkType : 获取当前网络类型
getIPAddress : 获取网络IP地址
getDomainAddress : 根据域名获取ip
```

> - **拼音相关→[PinyinUtils.java][PinyinUtils]**
```
ccs2Pinyin            : 中文转拼音
getPinyinFirstLetter  : 获取中文首字母
getPinyinFirstLetters : 获取所有中文首字母
```

> - **正则表达式相关→[RegexUtils.java][RegexUtils]**
```
isMobileSimple  : 验证手机号（简单）
isMobileExact   : 验证手机号（精确）
isTel           : 验证电话号码
isIDCard15      : 验证身份证号码15位
isIDCard18      : 验证身份证号码18位
isEmail         : 验证邮箱
isURL           : 验证URL
isZh            : 验证汉字
isUsername      : 验证用户名
isDate          : 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
isIP            : 验证IP地址
isMatch         : 判断是否匹配正则
getMatches      : 获取正则匹配的部分
getSplits       : 获取正则匹配分组
getReplaceFirst : 替换正则匹配的第一部分
getReplaceAll   : 替换所有正则匹配的部分
```

> - **命令相关→[ShellUtils.java][ShellUtils]**
```
execProceeBuilderCommand : 执行命令
execRuntimeCommand       : 执行命令
```

> - **字符串相关→[StringUtils.java][StringUtils]**
```
isEmpty : 判断字符串是否为空
```

> - **时间相关→[TimeUtils.java][TimeUtils]**
```
millisec2String  : 时间戳转时间字符串
string2Date      : 时间字符串转Date类型
string2Millisec  : 时间字符串转毫秒时间戳
date2String      : Date转时间字符串
getTimeSpan      : 获取两个时间差
getNowTimeMillis : 获取当前毫秒时间戳
getNowTimeDate   : 获取当前Date时间
getNowTimeString : 获取当前时间字符串
getTimeSpanByNow : 获取某时间与当前时间的差
isSameDay        : 判断时间是否是同一天
isToday          : 判断时间是否是今天
isLeapYear       : 判断是否是闰年
getWeek          : 获取星期几
getWeekOfMonth   : 获取月份中第几周
getWeekOfYear    : 获取年份中的第几周
getChineseZodiac : 获取生肖
getZodiac        : 获取星座
```

<br>

## Assist<a name="Assist">
> - **WeakHandler转载→[WeakHandler.java][WeakHandler]**
```
WeakHandler : 使用描述，性能优化，避免内存泄漏
```

> - **HanziToPinyin转载→[HanziToPinyin.java][HanziToPinyin]**
```
HanziToPinyin : Android汉字转拼音类
```

<br>

## 版本更新<a name="版本更新">
|            版本          |                              描述                               |
|------------------------- | -------------------------------------------------------------- |
| [1.2.2][BaseToolsV1.2.2] | 拓展配置文件存储  **2017-5-12** |
| [1.2.1][BaseToolsV1.2.1] | 支持通用适配器中的多种布局  **2017-4-20** |
| [1.2.0][BaseToolsV1.2.0] | 新增网络、拼音、异常打印等工具类  **2017-4-13** |
| [1.1.0][BaseToolsV1.1.0] | Utils增加一些常用的工具类:应用、数据库、分辨率、文件、正则表达式、命令、时间等  **2017-2-23** |
| [1.0.0][BaseToolsV1.0.0] | 创建ListView、GridView、RecyclerView的通用适配器，一些辅助方法  **2016-12-20** |

<br>

## 感谢<a name="感谢">
> - [张鸿洋][ZhangHongYang]
> - [布兰柯基][Blankj]
> - [马天宇][litesuits]

<br>

[返回目录](#目录)


<!-- 引用网站链接 -->
[Bintray]:https://bintray.com/veizhang/maven/basetools "Bintray"
[issues]:https://github.com/VeiZhang/BaseToolsLibrary/issues
[forks]:https://github.com/VeiZhang/BaseToolsLibrary/network/members
[stars]:https://github.com/VeiZhang/BaseToolsLibrary/stargazers

<!-- 图片链接 -->
[icon_Bintray]:https://img.shields.io/badge/Bintray-v1.2.2-brightgreen.svg
[icon_issues]:https://img.shields.io/github/issues/VeiZhang/BaseToolsLibrary.svg
[icon_forks]:https://img.shields.io/github/forks/VeiZhang/BaseToolsLibrary.svg?style=social
[icon_stars]:https://img.shields.io/github/stars/VeiZhang/BaseToolsLibrary.svg?style=social

<!-- 版本 -->
[BaseToolsV1.2.2]:https://bintray.com/veizhang/maven/basetools/1.2.2
[BaseToolsV1.2.1]:https://bintray.com/veizhang/maven/basetools/1.2.1
[BaseToolsV1.2.0]:https://bintray.com/veizhang/maven/basetools/1.2.0
[BaseToolsV1.1.0]:https://bintray.com/veizhang/maven/basetools/1.1.0
[BaseToolsV1.0.0]:https://bintray.com/veizhang/maven/basetools/1.0.0

<!-- 大神引用 -->
[ZhangHongYang]:https://github.com/hongyangAndroid/baseAdapter "通用适配器"
[Blankj]:https://github.com/Blankj/AndroidUtilCode "常用工具类"
[litesuits]:https://github.com/litesuits/android-common

<!-- 代码引用 -->
[GridAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/GridAdapterActivity.java
[RecyclerAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/RecyclerAdapterActivity.java
[MultiItemAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/MultiItemAdapterActivity.java
[MultiItemRecyclerAdapterActivity]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/tooldemo/src/main/java/com/excellence/tooldemo/MultiItemRecyclerAdapterActivity.java

<!-- 常用方法 -->
[ActivityUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ActivityUtils.java
[AppUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/AppUtils.java
[CloseUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/CloseUtils.java
[ConvertUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ConvertUtils.java
[DBUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/DBUtils.java
[DensityUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/DensityUtils.java
[EmptyUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/EmptyUtils.java
[ExceptionUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ExceptionUtils.java
[FileUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/FileUtils.java
[HandlerUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/HandlerUtils.java
[ImageUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ImageUtils.java
[NetworkUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/NetworkUtils.java
[PinyinUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/PinyinUtils.java
[RegexUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/RegexUtils.java
[ShellUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/ShellUtils.java
[StringUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/StringUtils.java
[TimeUtils]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/utils/TimeUtils.java

<!-- 转载方法 -->
[WeakHandler]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/assist/WeakHandler.java
[HanziToPinyin]:https://github.com/VeiZhang/BaseToolsLibrary/blob/master/basetoolslibrary/src/main/java/com/excellence/basetoolslibrary/assist/HanziToPinyin.java