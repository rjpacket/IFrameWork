package com.rjp.expandframework.utils.photo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.rjp.expandframework.interfaces.PermissionCallback;
import com.rjp.expandframework.utils.FileUtil;
import com.rjp.expandframework.utils.PermissionUtil;

import java.io.File;
import java.util.List;

/**
 * 拍照或者相册获取图片的中间件
 *
 * 裁剪的话按照正方形来，裁剪的宽高400px
 */
public class PhotoMiddleActivity extends Activity {

    public static final int PHOTO_TAKE = 200;
    public static final int PHOTO_PICK = 400;
    public static final String TYPE_PHOTO = "type_photo";
    public static final String CROP_PHOTO = "crop_photo";

    public static final int REQUEST_CODE_TAKE_PHOTO = 800;
    public static final int REQUEST_CODE_PICK_PHOTO = 1600;
    public static final int REQUEST_CODE_CROP_PHOTO = 3200;
    private boolean shouldCropPhoto;

    public static void start(Context context, int type, boolean crop) {
        Intent intent = new Intent(context, PhotoMiddleActivity.class);
        intent.putExtra(TYPE_PHOTO, type);
        intent.putExtra(CROP_PHOTO, crop);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (null != intent && intent.hasExtra(TYPE_PHOTO)) {
            shouldCropPhoto = intent.getBooleanExtra(CROP_PHOTO, false);
            int type = intent.getIntExtra(TYPE_PHOTO, -1);
            //无论是拍照或者选取图片都要首先请求权限
            if (type == PHOTO_TAKE) {
                new PermissionUtil.Builder()
                        .context(this)
                        .permission(Manifest.permission.CAMERA)
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .build()
                        .request(takeCallback);
            } else if (type == PHOTO_PICK) {
                new PermissionUtil.Builder()
                        .context(this)
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .build()
                        .request(pickCallback);
            }
        }
    }

    /**
     * 拍照请求权限回调
     */
    private PermissionCallback takeCallback = new PermissionCallback() {
        @Override
        public void allow() {
            take();
        }

        @Override
        public void deny(List<String> showDialog) {
            finish();
        }
    };

    /**
     * 选择照片权限回调
     */
    private PermissionCallback pickCallback = new PermissionCallback() {
        @Override
        public void allow() {
            pick();
        }

        @Override
        public void deny(List<String> showDialog) {
            finish();
        }
    };

    /**
     * 选择图片
     */
    private void pick() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_PICK_PHOTO);
    }

    /**
     * 拍照
     */
    private void take() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String takePhotoPath = FileUtil.getAppImagesPath(this) + File.separator + "takePhoto.png";
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtil.file2Uri(new File(takePhotoPath)));
        startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                if (shouldCropPhoto) {
                    String takePhotoPath = FileUtil.getAppImagesPath(this) + File.separator + "takePhoto.png";
                    String cropPhotoPath = FileUtil.getAppImagesPath(this) + File.separator + "cropPhoto.png";
                    cropPhoto(FileUtil.file2Uri(new File(takePhotoPath)), Uri.fromFile(new File(cropPhotoPath)));
                } else {
                    if (PhotoUtil.photoCallback != null) {
                        String takePhotoPath = FileUtil.getAppImagesPath(this) + File.separator + "takePhoto.png";
                        PhotoUtil.photoCallback.choosePhoto(takePhotoPath);
                    }
                    finish();
                }
            }else{
                finish();
            }
        } else if (requestCode == REQUEST_CODE_PICK_PHOTO) {
            if (resultCode == RESULT_OK) {
                if(data == null){
                    //小米机型这里可能为null，没有测试
                    finish();
                    return;
                }
                if (shouldCropPhoto) {
                    Uri pickUri = data.getData();
                    String cropPhotoPath = FileUtil.getAppImagesPath(this) + File.separator + "cropPhoto.png";
                    cropPhoto(pickUri, Uri.fromFile(new File(cropPhotoPath)));
                } else {
                    if (PhotoUtil.photoCallback != null) {
                        Uri pickUri = data.getData();
                        PhotoUtil.photoCallback.choosePhoto(FileUtil.getFilePathByUri(this, pickUri));
                    }
                    finish();
                }
            }else{
                finish();
            }
        } else if (requestCode == REQUEST_CODE_CROP_PHOTO) {
            if (resultCode == RESULT_OK) {
                if (PhotoUtil.photoCallback != null) {
                    String cropPhotoPath = FileUtil.getAppImagesPath(this) + File.separator + "cropPhoto.png";
                    PhotoUtil.photoCallback.choosePhoto(cropPhotoPath);
                }
                finish();
            }else{
                finish();
            }
        }
    }

    /**
     * 对拍照的图片进行裁剪
     * @param sourceUri
     * @param resultUri
     */
    private void cropPhoto(Uri sourceUri, Uri resultUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop",true);
        // aspectX,aspectY 是宽高的比例，这里设置正方形
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //设置要裁剪的宽高
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 400);
        intent.putExtra("scale",true);
        //如果图片过大，会导致oom，这里设置为false
        intent.putExtra("return-data",false);
        if (sourceUri != null) {
            intent.setDataAndType(sourceUri, "image/*");
        }
        if (resultUri != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, resultUri);
        }
        intent.putExtra("noFaceDetection", true);
        //压缩图片
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        startActivityForResult(intent, REQUEST_CODE_CROP_PHOTO);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PhotoUtil.onDestory();
    }
}
