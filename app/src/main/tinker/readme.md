### 一、 工程build.gradle引入配置

```groovy
    buildscript {
        repositories {
            jcenter()
        }
        dependencies {
            // TinkerPatch 插件
            classpath "com.tinkerpatch.sdk:tinkerpatch-gradle-plugin:1.2.13"
        }
    }
```

### 二、 app的build.gradle引入配置

```groovy
    dependencies {
        // 若使用annotation需要单独引用,对于tinker的其他库都无需再引用
        provided("com.tinkerpatch.tinker:tinker-android-anno:1.9.13")
        compile("com.tinkerpatch.sdk:tinkerpatch-android-sdk:1.2.13")
    }
```