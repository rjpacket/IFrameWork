### app的cpu使用率

6.0以前(应该包含6.0，未测试)可以通过查看/proc/stat文件里面的内容然后通过计算得出，步骤如下

    adb shell
    
    ls
    
    cd proc
    
    ls
    
    cat stat
    
就可以看到实时记录的数据。

但是7.0开始，这个/proc/stat不再对app开放，除非root，否则会报Permission denied的日志。虽然在cmd能打印，但是代码中读取到的是空数据。

### top 指令

    top -n 1
    
（第二个参数是数字一，不是L）

这样就能获取到cpu的数据。但是显示会有问题，测试每种手机的cpu数据列都不一样，很难定位截取，但是有规律，所有手机都是以PID为起始列，然后有些手机CPU的列会和其他列连在一起，又但是只要连在一起的，会用中括号区别。只要有规律，就可以用代码表示：

```

    public static void execShellGetCpuData(String packageName) {
        Process process = null;
        DataOutputStream os = null;
        BufferedReader in = null;

        try {
            process = Runtime.getRuntime().exec("sh");
            os = new DataOutputStream(process.getOutputStream());
            in = new BufferedReader(new InputStreamReader(process.getInputStream()));

            os.writeBytes("top -n 1\n");
            os.flush();
            os.writeBytes("exit\n");
            os.flush();

            String line = null;
            int index = -1;
            while ((line = in.readLine()) != null) {
                Log.d("===>", String.format("原始=%s", line));
                if (line.contains("PID") && line.contains("CPU")) {
                    String[] split = line.split("( )+");
                    int length = split.length;
                    boolean skip = true;
                    for (int i = 0; i < length; i++) {
                        Log.d("===>", String.format("当前列%s", split[i]));
                        if (skip && !"PID".equals(split[i])) {
                            skip = false;
                            continue;
                        }
                        index++;
                        if (split[i].contains("CPU")) {
                            if (split[i].contains("[")) {
                                index++;
                            }
                            break;
                        }
                    }
                } else {
                    //说明找到了
                    if (index != -1 && line.contains(packageName)) {
                        String[] titles = line.trim().split("( )+");
                        int length = titles.length;
                        for (int i = 0; i < length; i++) {
                            if (i == index) {
                                Log.d("===>", String.format("index=%s, cpu=%s", index, titles[i].replace("%", "")));
                            }
                        }
                        break;
                    }
                }
            }

            process.waitFor();
        } catch (Exception e) {
            
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {

            }
        }
    }

```


    

