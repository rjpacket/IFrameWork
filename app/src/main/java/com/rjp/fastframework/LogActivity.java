package com.rjp.fastframework;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rjp.expandframework.log.LogCallback;
import com.rjp.expandframework.log.OkLog;
import com.rjp.expandframework.utils.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class LogActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        mContext = this;
    }

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
