package com.rjp.expandframework.views.cameraView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.rjp.expandframework.utils.FileUtil;
import com.rjp.expandframework.utils.ScreenUtil;

/**
 * author : Gimpo create on 2018/12/10 11:46
 * email  : jimbo922@163.com
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    private Context mContext;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;

    public CameraView(Context context) {
        this(context, null);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mContext = context;
        mSurfaceHolder = getHolder();
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        //保持手机常亮
        mSurfaceHolder.setKeepScreenOn(true);
        mSurfaceHolder.addCallback(this);
    }

    public void startCameraPreview() {
        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(mSurfaceHolder);
                //摄像头角度调整
                mCamera.setDisplayOrientation(90);
                //开启摄像
                mCamera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
                //关闭摄像
                stopCameraView();
            }
        }
    }

    public void getCamera() {
        if (mCamera == null) {
            try {
                mCamera = Camera.open();

                //得到摄像头的参数
                Camera.Parameters parameters = mCamera.getParameters();
                //图片的格式
                parameters.setPictureFormat(ImageFormat.JPEG);
                //设置照片的质量
                parameters.setJpegQuality(100);
                //设置预览尺寸
                int screenWidth = ScreenUtil.getScreenWidth();
                int screenHeight = ScreenUtil.getScreenHeight();
                parameters.setPreviewSize(1920, 1080);
                //设置照片尺寸
                parameters.setPictureSize(1920, 1080);
                // 对焦
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                mCamera.setParameters(parameters);
            } catch (Exception e) {
                e.printStackTrace();
                mCamera = null;
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        getCamera();
        startCameraPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        stopCameraView();
        startCameraPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCameraView();
    }

    /**
     * 停止摄像 释放资源
     */
    private void stopCameraView() {
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }

    private void releaseCameraView() {
        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(null);
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 拍照
     */
    public void capture() {
        if (mCamera != null) {
            mCamera.takePicture(null, null, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    Bitmap bitmap = modifyPicktrueOrientation(0, BitmapFactory.decodeByteArray(data, 0, data.length));
                    FileUtil.saveBitmap(mContext, bitmap, "rjp.png");
                }
            });
        }
    }

    /**
     * 修正拍摄图片的角度
     *
     * @param cameraId
     * @param bitmap
     * @return
     */
    public Bitmap modifyPicktrueOrientation(int cameraId, Bitmap bitmap) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        return rotatingImageView(cameraId, info.orientation, bitmap);
    }

    /**
     * 把相机拍照返回照片转正
     *
     * @param angle 旋转角度
     * @return bitmap 图片
     */
    public Bitmap rotatingImageView(int id, int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        //加入翻转 把相机拍照返回照片转正
        if (id == 1) {
            matrix.postScale(-1, 1);
        }
        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
