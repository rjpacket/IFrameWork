package com.rjp.fastframework.home;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;

//import androidx.annotation.Nullable;

//import com.aykuttasil.callrecord.CallRecord;

/**
 * author: jinpeng.ren create at 2019/6/28 10:32
 * email: jinpeng.ren@11bee.com
 */
public class CallRecordService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
