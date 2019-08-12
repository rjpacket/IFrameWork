### 大量 if...else 模式 

使用极光推送的时候会有后台透传下来的消息，然后定义一个Type，安卓端根据这个Type去处理逻辑，所以在JPushReceiver里面的代码就存在大量的if...else...判断。除开阅读难度不讲，每一次定义一个新的Type带来的工作量也是很大的，而且还有风险，其他的处理逻辑会不会受到影响。

```java

    if("toast".equals(type)){
        //弹出toast
    }else if("popup".equals(type)){
        //弹出popup
    }
    
```

### 使用责任链模式改造

#### 1. 定义一个接口

```java

    public interface JPushInterceptor {
        boolean intercept(String type, String extra);
    }
    
```

#### 2. 责任链

```java

    public class RealJPushInterceptor {
    
        private List<JPushInterceptor> interceptors;
        private int index;
    
        public RealJPushInterceptor(List<JPushInterceptor> interceptors, int index){
            this.interceptors = interceptors;
            this.index = index;
        }
    
        public boolean intercept(String type, String extra){
            if(index >= interceptors.size()){
                return true;
            }
            JPushInterceptor jPushInterceptor = interceptors.get(index);
            boolean hasProcess = jPushInterceptor.intercept(type, extra);
            if(hasProcess){
                return true;
            }else{
                RealJPushInterceptor realJPushInterceptor = new RealJPushInterceptor(interceptors, index + 1);
                return realJPushInterceptor.intercept(type, extra);
            }
        }
    }
    
```

#### 3. 拦截器容器

```java

    public class JPushContainer {
        private List<JPushInterceptor> interceptors = new ArrayList<>();
    
        private JPushContainer(){}
    
        private static JPushContainer instance = null;
    
        public static JPushContainer getInstance(){
            if(instance == null){
                synchronized(JPushContainer.class){
                    if(instance == null){
                        instance = new JPushContainer();
                    }
                }
            }
            return instance;
        }
    
        public void addInterceptor(JPushInterceptor interceptor){
            getInterceptors().add(interceptor);
        }
    
        public List<JPushInterceptor> getInterceptors() {
            return interceptors;
        }
    }
    
```

#### 4. 实际开发需要的拦截器实例

```java

    public class ToastInterceptor implements JPushInterceptor {
        @Override
        public boolean intercept(String type, String extra) {
            if("toast".equals(type)){
    
                //处理toast
                return true;
            }
            return false;
        }
    }
    
    public class PopupInterceptor implements JPushInterceptor {
            @Override
            public boolean intercept(String type, String extra) {
                if("popup".equals(type)){
        
                    //处理popup
                    return true;
                }
                return false;
            }
        }
        
```

#### 5. 进入 Application 的时候注册

```java

    public void onCreate() {
        super.onCreate();
        JPushContainer.getInstance().addInterceptor(new ToastInterceptor());
        JPushContainer.getInstance().addInterceptor(new PopupInterceptor());
    }
    
```

### 可以愉快的发推送了