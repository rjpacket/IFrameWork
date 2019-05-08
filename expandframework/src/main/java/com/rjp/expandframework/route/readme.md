### 一、 页面跳转

两个页面之间的跳转

```
    Intent intent = new Intent();
    intent.setClass(A.this, B.class);
    startActivity(intent);
```

如果直接这样写，B文件删除了，会导致一系列报红，然后项目无法编译。

### 二、 使用配置文件

```
    Map<String, Class<?>> clazzMap = new HashMap<>();
    clazzMap.put("/a", A.class);
    clazzMap.put("/b", B.class);
    Intent intent = new Intent();
    Class<?> bClass = clazzMap.get("/b");
    if(bClass != null) {
        intent.setClass(A.this, bClass);
        startActivity(intent);
    }
```

维护一个classMap，跳转的时候从这个Map里面去找对应的页面，如果没有找到，可以做一些其他操作，比如提示，总体上影响不会太大，最差也就是无响应。

但是有个麻烦的地方在于，需要人工维护这个Map表，一旦文件删除了，但是这个表没有更新，还是会报错闪退。

### 三、 apt技术

什么是apt技术呢？Annotation processor Tool 注解处理工具。参照ARouter技术，简单的实现一个自动生成路由表的工作。

#### 3.1 首先定义一个注解路由

```
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.CLASS)
    public @interface Route {
    
        String path();
    }
```

首先TYPE代表它作用在类上，CLASS代表编译时期仍然存在，可以指定一个path参数，也就是路由表的key。

#### 3.2 定义路由bean

```
    public class RouteMeta {
    
        /**
         * 注解使用的类对象
         */
        private Class<?> destination;
    
        /**
         * 路由地址
         */
        private String path;
    
        public RouteMeta(Class<?> destination, String path) {
            this.destination = destination;
            this.path = path;
        }
    
        public Class<?> getDestination() {
            return destination;
        }
    
        public void setDestination(Class<?> destination) {
            this.destination = destination;
        }
    
        public String getPath() {
            return path;
        }
    
        public void setPath(String path) {
            this.path = path;
        }
    
    }
```

保存每一个路由的信息，简单的路由只需要path和class。

既然定义了注解就需要解释它。

#### 3.3 注解解释器

```
    @AutoService(Processor.class) //谷歌提供的服务，编译时期会自动回调这个类的各个方法
    public class RouteProcessor extends AbstractProcessor {
    
        @Override
        public Set<String> getSupportedAnnotationTypes() {
            Set<String> types = new LinkedHashSet<>();
            types.add(Route.class.getCanonicalName()); //指定支持的注解类型
            return types;
        }
    
        @Override
        public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
            //查找到所有被Route注解的class
            Set<? extends Element> routes = roundEnvironment.getElementsAnnotatedWith(Route.class);
            //自动生成文件
            processRoute(routes);
            return false;
        }
    
        private void processRoute(Set<? extends Element> routes) {
            try {
                //指定生成文件的包名
                String packageName = "com.rjp.fastframework";
                String activityFileName = packageName + ".IProject";
                final String CLASS_SUFFIX = "$Router";
                Filer filer = processingEnv.getFiler();
                //指定生成的文件名
                JavaFileObject javaFileObject = filer.createSourceFile(activityFileName + CLASS_SUFFIX);
                //定义writer，开始构建java文件
                Writer writer = javaFileObject.openWriter();
                //构建的类名
                String clazzName = "IProject" + CLASS_SUFFIX;
    
                writer.write("package " + packageName + ";\n");
                writer.write("import com.rjp.expandframework.route.bean.RouteMeta;\n");
                writer.write("import com.rjp.expandframework.route.interfaces.IRoute;\n");
                writer.write("import java.util.Map;\n");
                writer.write("public class " + clazzName + " implements IRoute {\n");
                writer.write("@Override\n");
                writer.write("public void loadRoute(Map<String, RouteMeta> routes) {\n");
    
                for (Element element : routes) {
                    String pageClass = element.asType().toString();
                    Route route = element.getAnnotation(Route.class);
                    writer.write("routes.put(\"" + route.path() + "\", new RouteMeta(" + pageClass + ".class, \"" + route.path() + "\"));\n");
                }
    
                writer.write("}\n");
                writer.write("}\n");
    
                writer.close();
            }catch (Exception e){
    
            }
        }
    }
```

#### 3.4 使用注解

```
    @Route(path = "/main/permission")
    public class PermissionActivity extends Activity {}
```

#### 3.5 自动生成路由文件

点击build，打开/app/build/generated/source/apt/{channel}/debug/，你就能看到自动生成的文件，里面已经自动化实现了以上writer编写的类代码。


