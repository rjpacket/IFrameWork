#### 01.

(垃圾回收机制)[http://jayfeng.com/2016/03/11/%E7%90%86%E8%A7%A3Java%E5%9E%83%E5%9C%BE%E5%9B%9E%E6%94%B6%E6%9C%BA%E5%88%B6/]

(JVM工作原理)[https://segmentfault.com/a/1190000002579346#articleHeader6]

#### 02.

(Blankj)[https://www.jianshu.com/u/46702d5c6978]

#### 03.

(java内存区域划分)[https://blog.csdn.net/weixin_28760063/article/details/81271237]

#### 04.

(public private final static)[https://www.cnblogs.com/hasse/p/5015475.html]

#### 05.

(Object中的11个方法)[https://blog.csdn.net/geyanyan0911/article/details/52997410]

#### 06.

(为什么重写equals方法要重写hashcode)[https://www.cnblogs.com/ysch/p/4323889.html]

#### 07.

(java的向上转型)[https://www.cnblogs.com/wypeng/p/7575257.html]

#### 08.

(强引用 软引用 弱引用 虚引用)[https://www.cnblogs.com/yw-ah/p/5830458.html]

#### 09.

“序列化”就是将运行时的对象状态转换成二进制，然后保存到流、内存或者通过网络传输给其他端。
一般在保存数据到 SD 卡或者网络传输时建议使用 Serializable 即可，虽然效率差一些，好在使用方便。
而在运行时数据传递时建议使用 Parcelable，比如 Intent，Bundle 等，Android 底层做了优化处理，效率很高。

#### 10.(打包流程)[]

首先.aidl（Android Interface Description Language）通过AIDL工具转换成编译器能处理的Java接口文件,如果项目没有用到AIDL则略过这一步骤,工具路径为：${ANDROID_SDK_HOME}/build-tools/aidl.exe。

资源文件被aapt(Android Asset Packaging Tool)处理为最终的resources.arsc，并生成R.java文件以保证源码编写时可以方便的访问这些资源,aapt工具的路径为 
${ANDROID_SDK_HOME}/build-tools/appt.exe。

java将生成R.java文件和源代码文件以及上述生成的接口文件统一编译成.class文件。

因为class文件并不是Android系统所能识别的格式，所以还要利用dex工具将它们转化成Dalvlik字节码。这个过程还会加入程序所依赖的所有第三方库,${ANDROID_SDK_HOME}/build-tools/dx.bat。

接下来，系统将会上面生成的dex,资源包，以及其它资源，通过apkbuilder生成初始的apk文件包。注意：此时，apk还没有经过签名和优化。

通过签名工具签名，例如Jarsinger等工具。如果在Debug模式下，签名所使用的keystore是系统自带的默认值，否则开发者需要提供自己的私钥来完成签名过程。

将签名后的apk通过zipalign进行优化。优化的目的是提高程序的运行和加载速度。基本原理是对apk包中的数据进行边界对齐，从而加快读取和处理过程,zipaligin工具路径为 ${ANDROID_SDK_HOME}/tools/zipalign.exe。


#### 11.(https://www.jianshu.com/p/6b31e650b347)[]

#### 12.()[]

#### 13.()[]

#### 14.()[]

