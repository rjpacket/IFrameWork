### 一、 新建任务
在jenkins首页，点击[新建任务]，输入任务名称[]，勾选[构建一个自由风格的软件项目]，点击左下角确定按钮

![2创建一个自由风格的项目.png](https://upload-images.jianshu.io/upload_images/5994029-2230b75567edfe93.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

好了我们的任务就创建好了，那怎么和我们手上的项目关联上呢？

### 二、 工程配置
进入项目，选择左侧栏的[配置]选项

![3工程配置.png](https://upload-images.jianshu.io/upload_images/5994029-8e863b4a3f976a9f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 2.1 源码管理

![4源码管理.png](https://upload-images.jianshu.io/upload_images/5994029-0cb2aff4abc24c9d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

在1的位置输入代码库的位置，点开2的Add，添加一下账户和密码的凭据，否则代码down不下来，3的位置设置当前构建的分支

#### 2.2 构建
Android项目目前基本都是借助Gradle构建，所以勾选[Invoke Gradle Script]，选择Use Gradle Wrapper，在 Tasks 里面写上 clean assembleRelease。注意主意，一定要点开高级选项，勾选如下内容，否则后面的参数化构建没有作用：

![5gradle设置.png](https://upload-images.jianshu.io/upload_images/5994029-672427a57f4f294b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

这是什么意思呢？就相当于你手动点击 build 下的assembleRelease，也就是打出正式包。

保存之后回到首页，现在可以自动构建了：

![6立即构建.png](https://upload-images.jianshu.io/upload_images/5994029-f73b1ca54f62a731.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![8工作空间的正式包.png](https://upload-images.jianshu.io/upload_images/5994029-3747e10876cb3781.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

构建完成之后，在工作区间app/outputs下就能看到自动生成的apk文件了，但是这样还不够，要想实现更多的功能，需要使用参数化构建。

#### 2.3 参数化构建
在参数化构建之前，我们需要明确打出来的app的名字由哪些部分组成：

```
    1.  app的名字
    2.  app的渠道
    3.  app的版本
    4.  app的构建类型
    5.  app构建的时间
```

明确这些之后，我们需要改造gradle文件。
找到项目根目录的gradle.properties，配置如下内容：

```
IS_JENKINS = false
APP_NAME = QiuLiao
PRODUCT_FLAVOR = channel_qiuliao
BUILD_TYPE = release
BUILD_TIME = 20181115
```

这些配置内容提前写上，后面要用上。
可以注意到版本的信息我们并没有配置，是有原因的，因为版本不需要手动配置，直接就能从项目里获取出来。

接下来改造app.gradle文件：
```
    android{
        applicationVariants.all {
            if(IS_JENKINS) {
                def appName = APP_NAME
                def product = PRODUCT_FLAVOR
                def appVersion = it.versionName
                def buildType = BUILD_TYPE
                def createTime = BUILD_TIME
                def fileName

                it.getPackageApplication().outputDirectory = new File(project.rootDir.absolutePath + "/auto-apks/${product}/${createTime}")
                it.outputs.each {
                    fileName = "${appName}-${product}-v${appVersion}-${buildType}-${createTime}.apk"
                    println "文件名：-----------------${fileName}"
                    it.outputFileName = fileName
                }
            }
        }
    }
```
这一段怎么解释呢，结合之前的配置文件看，就是将jenkins的输出目录修改为项目根目录下的auto-apks，并且将apk的名字格式化。

你可能会有疑问，这些配置参数都是写死的，什么时候修改呢？答案就是jenkins的参数注入。

#### 2.4 布尔型参数
点进项目配置，选择General，勾选参数化配置，点击添加参数，下拉选择一个布尔型参数

![9布尔型参数.png](https://upload-images.jianshu.io/upload_images/5994029-c7d3cd6ed785a70c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 2.5 字符参数
相当于String参数，配置我们的AppName：

![10字符参数.png](https://upload-images.jianshu.io/upload_images/5994029-1ab16018458d5d84.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 2.6 选项参数
列举出所有的选项，方便选择，我这个项目有两个ProductFlavor：

![11选项参数.png](https://upload-images.jianshu.io/upload_images/5994029-25899a74b893d81b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

同样，构建的类型也是两个选项：

![12选项参数-构建类型.png](https://upload-images.jianshu.io/upload_images/5994029-c0e7ce3826c9bd83.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 2.7 dynamic parameter
这个参数比较特别，需要安装插件，而且由于一些bug，在插件里是找不到的，需要手动安装。安装步骤移步[jenkins dynamic parameter plugin安装步骤]。

![13dynamic-parameter参数.png](https://upload-images.jianshu.io/upload_images/5994029-a26f36cab28e1a8b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 2.8 git parameter
这是选择构建的分支，当然你也可以选择tag，开发一般都是在分支下打包，所以直接选branch了：

![14git-parameter.png](https://upload-images.jianshu.io/upload_images/5994029-bc21e2d19285df4d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

注意一点，如果你设置了Branch，那么源码管理下的分支就要修改如下：

![15branch源码管理.png](https://upload-images.jianshu.io/upload_images/5994029-c0fe6e1ed9ac931f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

构建Tasks也要做出调整：

![16构建调整.png](https://upload-images.jianshu.io/upload_images/5994029-ad6ff9c42c27908c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

到这里基本的参数都配置完了，回到首页，发现左侧多了一个 [Build with Parameters] 选项，点击进入：

![17build-with-parameters.png](https://upload-images.jianshu.io/upload_images/5994029-2414b30fb78f4618.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

我们可以看到项目的分支都已经被读出来了，那么赶紧构建测试一下吧。

### 三、 构建之后
可以看到左下角的蓝色按钮，也就是构建成功了，同时打开工作区间，发现apk也已经被构建出来了，参数也在apk上带着：

![18build-success.png](https://upload-images.jianshu.io/upload_images/5994029-e3b95b5296de0f65.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

虽然构建完成了，但是一我们页面上看不到构建的信息，二我们没有收到邮件通知，这些也是需要配置的。

#### 3.1 构建的信息
继续进入配置，点击 [构建后操作] ，点击 [set build description]：

配置内容：
```
构建名称：${APP_NAME}<br>构建产品：${PRODUCT_FLAVOR}<br>构建分支：${BRANCH}<br>构建类型：${BUILD_TYPE}<br>构建时间：${BUILD_TIME}<br>
```

#### 3.2 邮件通知
在通知之前，我们要增加一个邮件列表参数，方便我们增加需要接收的人的列表：

![20邮件参数.png](https://upload-images.jianshu.io/upload_images/5994029-905bf35a4b8b5b39.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

再拉到最后点击[增加构建后操作步骤]，选择[Editable Email Notification]：

![19邮件设置.png](https://upload-images.jianshu.io/upload_images/5994029-e4433391cecaad0a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

这里有一个需要注意的，就是第1行一定要保持为空，否则收不到信息；(官方bug)
第2行就是会收到的列表人；
第3行是收件人的邮件标题；
第4行是收件人的邮件内容。
设置完上面四个内容，还要点开高级设置：

![21触发邮件.png](https://upload-images.jianshu.io/upload_images/5994029-a57745de9596a831.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

触发发邮件的条件和修改接收邮件的人，并不是开发者，而是我们上面的第2行列表。

好了继续构建一次：

![22set-build-description.png](https://upload-images.jianshu.io/upload_images/5994029-da145f682cffd337.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

打开邮箱：

![23邮件内容.png](https://upload-images.jianshu.io/upload_images/5994029-b811b5e25be2068c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

如果你看到上面的内容可以说成功90了。但是，如果测试收到了，他肯定很难受，因为没有地方可以下载构建成功的apk。

#### 3.3 上传蒲公英 或者 fir
这里我选择了蒲公英插件，直接搜索pgyer。同样进入配置，拉到最后，选择[增加构建后操作步骤]，选择[Upload to pgyer with apiV1]

![24pgyer.png](https://upload-images.jianshu.io/upload_images/5994029-6729dc340626ec85.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

第1行第2行就是正常的key，你从蒲公英官网就能拿到；
第3行是插件会去扫描的目录，而且只扫描当前层级，不会进入子目录；
第4行是插件要上传的目标apk。

上传完成，插件会在全局变量里生成一堆的值，我们只关心下载地址appBuildURL和二维码地址appQRCodeURL。

拿到这两个值，我们可以格式一下邮件的内容：
```
    （以下内容来自jenkins自动转发，请勿回复）<br>
    构建项目：${APP_NAME}<br>
    构建产品：${PRODUCT_FLAVOR}<br>
    构建分支：${BRANCH}<br>
    构建类型：${BUILD_TYPE}<br>
    下载地址：<a href='${appBuildURL}'>${appBuildURL}</a><br>
    扫二维码：<img src='${appQRCodeURL}' width=200 height=200 /><br>
```

保存，再来一次构建，打开邮箱看一下：

![25邮箱二维码.png](https://upload-images.jianshu.io/upload_images/5994029-a262e5ab2648cc31.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

齐活。

如果想要自己在构建页面也看到二维码，简单，修改3.1的内容，把邮箱里的html搬过去就行。

### 四、 最后
1. 如果app是敏感的内容，不想放到第三方保管，怎么办？
2. 如果发包之前还要求加固怎么办？
