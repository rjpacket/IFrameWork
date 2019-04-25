#### 1. 编写native方法

```
    public class Hello {
        public static native String sayHi();
    }
```

系统会告知没有找到这个JNI方法，利用javah命令行生成这个c的头文件。

#### 2. 进入目录app/src/main/java，执行javah -d ../jni com.rjp.fastframework.Hello

会在main下生成jni目录，同时生成com_rjp_fastframework_Hello.h文件。

#### 3. 创建hello.c文件，编写c逻辑

从 .h 文件拷贝方法结构体，完成 c 逻辑。

#### 4. 添加Android.mk和Application.mk文件

```
    LOCAL_PATH := $(call my-dir)
    
    include $(CLEAR_VARS)
    
    LOCAL_MODULE    := hello
    
    LOCAL_SRC_FILES := hello.c
    
    include $(BUILD_SHARED_LIBRARY)
```

Application.mk指定生成的so包类型

```
    APP_ABI := all
```

#### 5. 配置gradle文件

```
    defaultConfig{
        ndk{
                moduleName "hello"
                abiFilters "armeabi", "armeabi-v7a" //没有起作用
           }
    }
    
    sourceSets{
        main{
            jni.srcDirs = [] 
            jniLibs.srcDir 'src/main/libs'
        }
    }
```

#### 6. 进入jni目录，执行命令ndk-build

完成之后可以看到生成的so包了。使用静态代码块加载so包就可以调用c的方法了。

```
    static {
        System.loadLibrary("hello");
    }
```