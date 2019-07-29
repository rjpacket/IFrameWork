package com.rjp.expandframework.apm.cpu;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.rjp.expandframework.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    EditText editText_times,editText_min,editText_package;
    Button button_top,button_reporte,cpu_btn,start_app,start_time;
    TextView countDown,cpu_max,cpu_min,cpu_ave,rss_max,rss_min,rss_ave,vss_max,vss_min,vss_ave;
    TextView total_time,wait_time,completa_time;
    FileUtils fu=new FileUtils();
    List cpu,rss,vss=new ArrayList<String>();

    ListUtils lu=new ListUtils();

    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cpu_main);
        initView();
   //监控详情按钮，点击后通过intent传值到新的activity
        cpu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    cpu= fu.readcpu("/mnt/sdcard/topInfo.txt",editText_package);
                    rss= fu.readrss("/mnt/sdcard/topInfo.txt",editText_package);
                    vss=fu.readvss("/mnt/sdcard/topInfo.txt",editText_package);

                    String times=fu.read("/mnt/sdcard/totime.csv");
                    String totalTime=fu.read("/mnt/sdcard/totaltime.csv");

                    Intent intent = new Intent(MainActivity.this,TopDetailActivity.class);



                    intent.putExtra("times",times);
                    intent.putExtra("totalTime",totalTime);

                    intent.putStringArrayListExtra("cpu", (ArrayList) cpu);//key就是自己定义一个String的字符串就行了
                    intent.putStringArrayListExtra("vss", (ArrayList) vss);
                    intent.putStringArrayListExtra("rss", (ArrayList) rss);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast toast=Toast.makeText(MainActivity.this, "监控文件被删除", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


//监控报告按钮
        button_reporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {//在run()方法实现业务逻辑；
                        //...

                        //更新UI操作；
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                List<String>str_cpu= null;
                                List<String>str_vss= null;
                                List<String>str_rss= null;
                                try {
                                    str_cpu = fu.readcpu("/mnt/sdcard/topInfo.txt",editText_package);
                                    str_rss=fu.readrss("/mnt/sdcard/topInfo.txt",editText_package);
                                    str_vss=fu.readvss("/mnt/sdcard/topInfo.txt",editText_package);

                                    List cpu_info=lu.StrToInt(str_cpu);
                                    List rss_info=lu.StrToInt(str_rss);
                                    List vss_info=lu.StrToInt(str_vss);
                                    double ave=lu.listAverage(cpu_info);
                                    double ave1=lu.listAverage(rss_info);
                                    double ave2=lu.listAverage(vss_info);


                                    Log.i("cpuinfo", Collections.max(str_cpu));
                                    Log.i("cpuinfo", Collections.min(str_cpu));
                                    Log.i("cpuinfo", cpu_info+"");
                                    Log.i("cpuinfo", ave+"");
                                    cpu_max.setText(Collections.max(str_cpu)+"%");
                                    cpu_min.setText(Collections.min(str_cpu)+"%");
                                    cpu_ave.setText(ave+"%");

                                    rss_max.setText(Collections.max(str_rss)+"K");
                                    rss_min.setText(Collections.min(str_rss)+"K");
                                    rss_ave.setText(ave1+"K");

                                    vss_max.setText(Collections.max(str_vss)+"K");
                                    vss_min.setText(Collections.min(str_vss)+"K");
                                    vss_ave.setText(ave2+"K");


                                } catch (IOException e) {
                                    Toast toast=Toast.makeText(MainActivity.this, "监控失败", Toast.LENGTH_SHORT);
                                    toast.show();
                                }catch (NoSuchElementException elementException){
                                    Toast toast=Toast.makeText(MainActivity.this, "监控应用未启动", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            }
                        });

                    }
                }.start();
            }
        });

        //监控按钮
        button_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CpuMonitor.execShellGetCpuData("com.rjp.AAAAA");
            }
        });


    }

    private void initView() {
        editText_min=(EditText) findViewById(R.id.ed_topmin);
        editText_times=(EditText)findViewById(R.id.ed_toptimes);
        editText_package=(EditText)findViewById(R.id.ed_package);
        button_top=(Button)findViewById(R.id.button_topbegin);
        button_reporte=(Button) findViewById(R.id.button_reporte);
        cpu_btn=(Button)findViewById(R.id.cpu_btn);



        countDown=(TextView)findViewById(R.id.timer);
        cpu_max=(TextView)findViewById(R.id.cpu_max);
        cpu_min=(TextView)findViewById(R.id.cpu_min);
        cpu_ave=(TextView) findViewById(R.id.cpu_ave);
        rss_max=(TextView) findViewById(R.id.rss_max);
        rss_min=(TextView)findViewById(R.id.rss_min);
        rss_ave=(TextView) findViewById(R.id.rss_ave);

        vss_max=(TextView) findViewById(R.id.vss_max);
        vss_min=(TextView) findViewById(R.id.vss_min);
        vss_ave=(TextView)findViewById(R.id.vss_ave);

    }

    public void threadpool(int case_num) {
        ExecutorService cacheThreadPool = Executors.newFixedThreadPool(5);
        if (case_num == 5) {
            cacheThreadPool.execute(new Runnable() {
                // final int index =1;
                @Override
                public void run() {
                    // String times=editText_times.getText().toString();
                    String times = editText_times.getText().toString();
                    //监控间隔时间
                    int times_num = Integer.parseInt(times);
                    String min = editText_min.getText().toString();
                    //监控总时间
                    int min_num = Integer.parseInt(min);
                    //次数
                    int n = min_num * 60 / times_num;

                    //如果监控文件不存在则创建文件
                    if (fu.topfileIsExists("/mnt/sdcard/totaltime.csv") == false) {
                        ShellUtils.execCommand("touch /mnt/sdcard/totaltime.csv", true);
                    }
                    if (fu.topfileIsExists("/mnt/sdcard/totime.csv") == false) {
                        ShellUtils.execCommand("touch /mnt/sdcard/totime.csv", false);
                    }

                    fu.writeTofile("/mnt/sdcard/totaltime.csv", min);
                    fu.writeTofile("/mnt/sdcard/totime.csv", times);
                    ShellUtils.execCommand("top -m 5 -d " + times + " -n " + n + " >/mnt/sdcard/topInfo.txt", false);

                }
            });

        }

    }

}