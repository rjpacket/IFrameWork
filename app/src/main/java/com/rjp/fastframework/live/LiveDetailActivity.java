package com.rjp.fastframework.live;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.rjp.fastframework.R;

import java.util.List;

public class LiveDetailActivity extends Activity implements SurfaceHolder.Callback, Camera.PreviewCallback {

    public static final int FRAMERATE_DEF = 3;
    public static final int HEIGHT_DEF = 1920;
    public static final int WIDTH_DEF = 1080;

    public static final String ROOM_URL = "room_url";
    private String roomUrl;
    private Context mContext;
    private SurfaceView surfaceView;
    private boolean cameraIsFront;
    private Camera mCamera;
    private int cameraCodecType;
    private int cameraDegree;

    public static void trendTo(Context mContext, String roomUrl) {
        Intent intent = new Intent(mContext, LiveDetailActivity.class);
        intent.putExtra(ROOM_URL, roomUrl);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_detail);

        mContext = this;

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(ROOM_URL)) {
            roomUrl = intent.getStringExtra(ROOM_URL);
            initView();
        }
    }

    private void initView() {
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        int iNewWidth = (int) (height * 3.0 / 4.0);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        int iPos = width - iNewWidth;
        layoutParams.setMargins(iPos, 0, 0, 0);

        surfaceView = findViewById(R.id.surface_view);
        surfaceView.getHolder().setFixedSize(HEIGHT_DEF, WIDTH_DEF);
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().setKeepScreenOn(true);
        surfaceView.getHolder().addCallback(this);
        surfaceView.setLayoutParams(layoutParams);


//        _SwitchCameraBtn = (Button) findViewById(R.id.SwitchCamerabutton);
//        _SwitchCameraBtn.setOnClickListener(_switchCameraOnClickedEvent);

        initAudioRecord(); // 初始化音频
        startPushStream();//开始推流
    }

    private void initAudioRecord() {

    }

    private void startPushStream() {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        cameraDegree = getDisplayOritation(getDispalyRotation(), 0);
        if (mCamera != null) {
            initCamera(); //初始化相机
            return;
        }
        //华为i7前后共用摄像头
        if (Camera.getNumberOfCameras() == 1) {
            cameraIsFront = false;
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        } else {
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        }
        initCamera();
    }

    private int getDisplayOritation(int degrees, int cameraId) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        return result;
    }

    private int getDispalyRotation() {
        int i = getWindowManager().getDefaultDisplay().getRotation();
        switch (i) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
        return 0;
    }

    /**
     * 初始化相机
     */
    private void initCamera() {
        Camera.Parameters p = mCamera.getParameters();

        Camera.Size prevewSize = p.getPreviewSize();
        Log.d("----------->", "Original Width:" + prevewSize.width + ", height:" + prevewSize.height);

        List<Camera.Size> PreviewSizeList = p.getSupportedPreviewSizes();
        List<Integer> PreviewFormats = p.getSupportedPreviewFormats();
        Log.d("----------->", "Listing all supported preview sizes");
        for (Camera.Size size : PreviewSizeList) {
            Log.d("----------->", "  w: " + size.width + ", h: " + size.height);
        }

        Log.d("----------->", "Listing all supported preview formats");
        Integer iNV21Flag = 0;
        Integer iYV12Flag = 0;
        for (Integer yuvFormat : PreviewFormats) {
            Log.d("----------->", "preview formats:" + yuvFormat);
            if (yuvFormat == android.graphics.ImageFormat.YV12) {
                iYV12Flag = android.graphics.ImageFormat.YV12;
            }
            if (yuvFormat == android.graphics.ImageFormat.NV21) {
                iNV21Flag = android.graphics.ImageFormat.NV21;
            }
        }

        if (iNV21Flag != 0) {
            cameraCodecType = iNV21Flag;
        } else if (iYV12Flag != 0) {
            cameraCodecType = iYV12Flag;
        }
        p.setPreviewSize(HEIGHT_DEF, WIDTH_DEF);
        p.setPreviewFormat(cameraCodecType);
        p.setPreviewFrameRate(FRAMERATE_DEF);

        Log.d("----------->", "cameraDegree=" + cameraDegree);
        mCamera.setDisplayOrientation(cameraDegree);
        p.setRotation(cameraDegree);
        mCamera.setPreviewCallback(this);
        mCamera.setParameters(p);
        try {
            mCamera.setPreviewDisplay(surfaceView.getHolder());
        } catch (Exception e) {
            return;
        }
        mCamera.cancelAutoFocus();//只有加上了这一句，才会自动对焦。
        mCamera.startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

    }
}
