package com.rjp.expandframework.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.*;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.rjp.expandframework.R;
import com.rjp.expandframework.apm.cpu.FileUtils;
import com.rjp.expandframework.interfaces.PermissionCallback;
import com.rjp.expandframework.utils.FileUtil;
import com.rjp.expandframework.utils.PermissionUtil;

import java.io.File;
import java.util.List;

public class Html5Activity extends AppCompatActivity {

    private String mUrl;

    private LinearLayout mLayout;
    private WebView mWebView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mContext = this;

        Bundle bundle = getIntent().getBundleExtra("bundle");
        mUrl = bundle.getString("url");

        mLayout = findViewById(R.id.web_layout);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(getApplicationContext());
        mWebView.setLayoutParams(params);
        mLayout.addView(mWebView);

        WebSettings mWebSettings = mWebView.getSettings();
        //基础设置
        mWebSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        //放大缩小
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setDisplayZoomControls(false);
        mWebSettings.setDefaultTextEncodingName("utf-8");
        //文件缓存
        mWebSettings.setAllowFileAccess(true);
        if (isConnected(getApplicationContext())) {
            mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
        } else {
            mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
        }
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setAppCacheEnabled(true);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        mWebSettings.setAppCachePath(appCachePath);
        //支持多窗口
        mWebSettings.setSupportMultipleWindows(false);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //混合http https
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //是否自动加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            mWebSettings.setLoadsImagesAutomatically(true);
        } else {
            mWebSettings.setLoadsImagesAutomatically(false);
        }
        //关闭硬件加速 防止页面闪烁
        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(webViewClient);
        mWebView.loadUrl(mUrl);
    }

    public boolean isConnected(Context context) {
        boolean connected = false;
        try {
            connected = ((ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo().isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connected;
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
        mWebView.pauseTimers(); //小心这个！！！暂停整个 WebView 所有布局、解析、JS。
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
        mWebView.resumeTimers();
    }

    /**
     * 多窗口的问题
     */
    private void newWin(WebSettings mWebSettings) {
        //html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
        //然后 复写 WebChromeClient的onCreateWindow方法

    }

    /**
     * HTML5数据存储
     */
    private void saveData(WebSettings mWebSettings) {
        //有时候网页需要自己保存一些关键数据,Android WebView 需要自己设置

    }

    WebViewClient webViewClient = new WebViewClient() {

        /**
         * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            if (!("http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme()))) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                return true;
            }
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            //网页开始

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
                mWebView.getSettings().setLoadsImagesAutomatically(true);
            }
            super.onPageFinished(view, url);
            //网页结束

        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
//            customErrorView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
            handler.proceed();
        }
    };

    private ValueCallback mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessages;
    public static final int FILE_CHOOSE_CODE = 8889;

    WebChromeClient webChromeClient = new WebChromeClient() {

        private View videoView;
        private ViewGroup mContentView;

        //=========HTML5定位==========================================================
        //需要先加入权限
        //<uses-permission android:name="android.permission.INTERNET"/>
        //<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
        //<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
        }

        @Override
        public void onGeolocationPermissionsHidePrompt() {
            super.onGeolocationPermissionsHidePrompt();
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);//注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
        //=========HTML5定位==========================================================

        //=========多窗口的问题==========================================================
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(view);
            resultMsg.sendToTarget();
            return true;
        }
        //=========多窗口的问题==========================================================

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
            mContentView = (ViewGroup) ((ViewGroup) (((Activity) mContext).findViewById(android.R.id.content))).getChildAt(0);
            mContentView.addView(view);
            view.setBackgroundColor(Color.BLACK);
            videoView = view;
//            oritation = ((Activity)context).getRequestedOrientation();
//            ((Activity)context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }

        @Override
        public void onHideCustomView() {
            super.onHideCustomView();
            if (videoView != null && mContentView != null) {
                mContentView.removeView(videoView);
                videoView = null;
            }
//            ((Activity)context).setRequestedOrientation(oritation);
        }

        protected void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            fileChooser();
        }

        protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            fileChooser();
        }

        protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            fileChooser();
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            mUploadMessages = filePathCallback;
            fileChooser();
            return true;
        }
    };

    /**
     * 自定义选择拍照或者相册
     */
    private void fileChooser() {
        PermissionUtil.builder().context(mContext)
                .permission(Manifest.permission.CAMERA)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .build()
                .request(new PermissionCallback() {
                    @Override
                    public void allow() {
                        realFileChooser();
                    }

                    @Override
                    public void deny(List<String> showDialog) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            if (mUploadMessages != null) {
                                mUploadMessages.onReceiveValue(null);
                                mUploadMessages = null;
                            }
                        } else {
                            if (mUploadMessage != null) {
                                mUploadMessage.onReceiveValue(null);
                                mUploadMessage = null;
                            }
                        }
                    }
                });
    }

    private void realFileChooser() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(FileUtil.getAppImagesPath(mContext), "webview_temp.png")));

        Intent pickIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pickIntent.addCategory(Intent.CATEGORY_OPENABLE);
        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        pickIntent.setType("*/*");

        Intent chooseIntent = new Intent(Intent.ACTION_CHOOSER);
        chooseIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});
        chooseIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
        chooseIntent.putExtra(Intent.EXTRA_INTENT, pickIntent);
        ((Activity) mContext).startActivityForResult(chooseIntent, FILE_CHOOSE_CODE);
    }

    /**
     * 回调选择文件之后
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == FILE_CHOOSE_CODE && resultCode == RESULT_OK) {
                if (mUploadMessages == null)
                    return;
                if (data == null) {
                    File photoFile = new File(FileUtil.getAppImagesPath(mContext), "webview_temp.png");
                    if (photoFile.exists()) {
                        Uri fileUri = Uri.fromFile(photoFile);
                        mUploadMessages.onReceiveValue(new Uri[]{fileUri});
                        mUploadMessages = null;
                    }else{
                        mUploadMessages.onReceiveValue(null);
                        mUploadMessages = null;
                    }
                } else {
                    ClipData clipData = data.getClipData();
                    if (clipData != null) {
                        int itemCount = clipData.getItemCount();
                        Uri[] uris = new Uri[itemCount];
                        for (int i = 0; i < itemCount; i++) {
                            Uri uri = clipData.getItemAt(i).getUri();
                            uris[i] = uri;
                        }
                        mUploadMessages.onReceiveValue(uris);
                        mUploadMessages = null;
                    } else {
                        mUploadMessages.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
                        mUploadMessages = null;
                    }
                }
            } else {
                if (mUploadMessages != null) {
                    mUploadMessages.onReceiveValue(null);
                    mUploadMessages = null;
                }
            }
        } else if (requestCode == FILE_CHOOSE_CODE && resultCode == RESULT_OK) {
            if (null == mUploadMessage)
                return;
            if (data == null) {
                File photoFile = new File(FileUtil.getAppImagesPath(mContext), "webview_temp.png");
                if (photoFile.exists()) {
                    Uri fileUri = Uri.fromFile(photoFile);
                    mUploadMessage.onReceiveValue(fileUri);
                    mUploadMessage = null;
                } else {
                    mUploadMessage.onReceiveValue(null);
                    mUploadMessage = null;
                }
            } else {
                Uri result = data.getData();
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        } else {
            Toast.makeText(mContext, "不支持上传", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.stopLoading();
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

}