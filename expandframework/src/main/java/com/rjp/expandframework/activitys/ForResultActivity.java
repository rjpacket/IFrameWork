package com.rjp.expandframework.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.rjp.expandframework.R;
import com.rjp.expandframework.utils.ActivityUtil;

/**
 * 某一个页面 startActivityForResult 的中间页
 */
public class ForResultActivity extends Activity {

    //请求的页面需要将数据打包成进这个bundle
    public static final String TRANS_BUNDLE = "trans_bundle";

    //中转过来的bundle
    public static final String FOR_RESULT_BUNDLE = "for_result_bundle";
    //请求码
    public static final String REQUEST_CODE = "for_result_request_code";
    //目的页面
    public static final String REQUEST_CLASS = "for_result_request_class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_result);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(FOR_RESULT_BUNDLE)) {
            Bundle bundleExtra = intent.getBundleExtra(FOR_RESULT_BUNDLE);
            int requestCode = bundleExtra.getInt(REQUEST_CODE);
            String requestClass = bundleExtra.getString(REQUEST_CLASS);
            try {
                Intent intentLast = new Intent(this, Class.forName(requestClass));
                intentLast.putExtra(TRANS_BUNDLE, bundleExtra);
                startActivityForResult(intentLast, requestCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != ActivityUtil.listener) {
            ActivityUtil.listener.onActivityResult(requestCode, resultCode, data);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        //防止内存泄露
        ActivityUtil.listener = null;
        super.onDestroy();
    }
}
