### 一、一行代码实现拍照或选择照片

```
    btnTake.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PhotoUtil.takePhoto(mContext, false, new PhotoCallback() {
                @Override
                public void choosePhoto(String photoPath) {
                    btnTake.setText(photoPath);
                }
            });
        }
    });

    btnPick.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PhotoUtil.pickPhoto(mContext, false, new PhotoCallback() {
                @Override
                public void choosePhoto(String photoPath) {
                    btnPick.setText(photoPath);
                }
            });
        }
    });
    
    btnTakeCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoUtil.takePhoto(mContext, true, new PhotoCallback() {
                    @Override
                    public void choosePhoto(String photoPath) {
                        btnTakeCrop.setText(photoPath);
                    }
                });
            }
        });

    btnPickCrop.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PhotoUtil.pickPhoto(mContext, true, new PhotoCallback() {
                @Override
                public void choosePhoto(String photoPath) {
                    btnPickCrop.setText(photoPath);
                }
            });
        }
    });
```

分别是拍照、选择照片、拍照裁剪和选择照片裁剪。

### 二、 具体实现

#### 2.1 定义回调接口

```
    public interface PhotoCallback {
        void choosePhoto(String photoPath);
    }
```

#### 2.2 Data中转页面

拍照的麻烦之处在于需要在用到的页面重写 onActivityResult() 方法，这样代码就显得特别乱，阅读难度大，通过一个中间页面来做这个操作，大大减小了代码量，代码只需要关注逻辑，不需要知道具体实现。

```
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
        public void deny() {
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
        public void deny() {
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

        //此处一定要加临时权限，否则裁剪会报加载失败
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
```

这样所有的请求权限和拍完照之后的获取Data全部交给中间页面处理，我们只需要把照片的路径获取到，回调给需要的页面就行了。

#### 2.3 工具类

```
public class PhotoUtil {

    public static PhotoCallback photoCallback;

    /**
     * 拍照向外暴漏的方法
     * @param context
     * @param crop
     * @param callback
     */
    public static void takePhoto(Context context, boolean crop, PhotoCallback callback){
        photoCallback = callback;
        PhotoMiddleActivity.start(context, PHOTO_TAKE, crop);
    }

    /**
     * 选择照片暴漏的方法
     * @param context
     * @param crop
     * @param callback
     */
    public static void pickPhoto(Context context, boolean crop, PhotoCallback callback){
        photoCallback = callback;
        PhotoMiddleActivity.start(context, PHOTO_PICK, crop);
    }

    /**
     * 释放资源
     */
    public static void onDestory(){
        photoCallback = null;
    }
}
```

具体用到的时候我们也不要直接和中间页面去交互，直接通过工具类的封装，从全局的静态回调对象里拿到图片的路径就行了。

最后一点需要注意的，在中间页面销毁的时候，需要把静态对象置空，否则会有内存泄漏的风险。