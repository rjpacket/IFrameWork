package com.rjp.fastframework;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.rjp.expandframework.bitmap.OkBitmap;
import com.rjp.expandframework.interfaces.PermissionCallback;
import com.rjp.expandframework.utils.DialogUtil;
import com.rjp.expandframework.utils.FileUtil;
import com.rjp.expandframework.utils.PermissionUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LogActivity extends AppCompatActivity {

    public static final String IMG = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1092143506,45115176&fm=26&gp=0.jpg";

    private Context mContext;
    private ThreadPoolExecutor mThreadPoolExecutor;
    private ImageView iv01;
    private ImageView iv02;
    private ImageView iv03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        mContext = this;

        BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
        mThreadPoolExecutor = new ThreadPoolExecutor(
                2,
                2,
                15,
                TimeUnit.SECONDS,
                taskQueue,
                Executors.defaultThreadFactory(),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

                    }
                }
        );

//        mThreadPoolExecutor.execute(thread1);
//        mThreadPoolExecutor.execute(thread2);

        iv01 = findViewById(R.id.iv_01);
        iv01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkBitmap.load(mContext, iv01, IMG);
            }
        });

        iv02 = findViewById(R.id.iv_02);
        iv02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkBitmap.load(mContext, iv02, IMG);
            }
        });

        iv03 = findViewById(R.id.iv_03);
        iv03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkBitmap.load(mContext, iv03, IMG);
            }
        });

        WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        DhcpInfo wifiInfo = wifiManager.getDhcpInfo();
        String ipAddress = intIP2StringIP(wifiInfo.gateway);//得到IPV4地址

        final Button btn = findViewById(R.id.btn_text);
        btn.setText("success");
    }

    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    public Runnable thread1 = new Runnable() {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("===>", "1");
            }
        }
    };

    public Runnable thread2 = new Runnable() {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("===>", "2");
            }
        }
    };

    public void writeLog(View view){
//        OkLog.save("1234567890123456789012345678901234567890", new LogCallback() {
//            @Override
//            public void onSuccess(String path) {
//                Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure() {
//                Toast.makeText(mContext, "保存失败", Toast.LENGTH_SHORT).show();
//            }
//        });

        String testData = "123456789012345678901234567890";
        long t1 = System.currentTimeMillis();
        write(testData);
        System.out.println("1耗时：" + (System.currentTimeMillis() - t1) + "ms");


        t1 = System.currentTimeMillis();
        writeByMap(testData);
        System.out.println("2耗时：" + (System.currentTimeMillis() - t1) + "ms");

        PermissionUtil.builder().context(this).permission(Manifest.permission.CAMERA)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .build()
                .request(new PermissionCallback() {
                    @Override
                    public void allow() {
                        take();
                    }

                    @Override
                    public void deny(List<String> showDialog) {
                        if(showDialog != null && showDialog.size() > 0) {
                            new DialogUtil.Builder()
                                    .context(mContext)
                                    .notice(PermissionUtil.getNotice(showDialog))
                                    .build()
                                    .show();
                        }
                    }
                });
    }

    /**
     * 拍照
     */
    private void take() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String takePhotoPath = FileUtil.getAppImagesPath(this) + File.separator + "takePhoto.png";
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtil.file2Uri(new File(takePhotoPath)));
        startActivityForResult(intent, 0);
    }

    private void writeByBuffer(String testData) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(FileUtil.getAppLogsPath(mContext), "log2.txt"), true)));
            for (int i = 0; i < 10000; i++) {
                writer.write(testData);
            }
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void writeByMap(String testData) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(new File(FileUtil.getAppLogsPath(mContext), "log1.txt"), "rw");
            MappedByteBuffer buff = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 1024 * 1024);
            for (int i = 0; i < 10000; i++) {
                buff.put(testData.getBytes());
            }
            buff.force();
            buff.flip();
        } catch (Exception e) {
            Log.e("TEST", "new: " + e.toString());
        } finally {
            if(raf != null){
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void write(String testData) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File(FileUtil.getAppLogsPath(mContext), "log1.txt"), true);
            for (int i = 0; i < 10000; i++) {
                outputStream.write(testData.getBytes());
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
