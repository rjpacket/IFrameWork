package com.rjp.expandframework.utils;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import com.rjp.expandframework.BuildConfig;
import com.rjp.expandframework.activitys.PermissionActivity;
import com.rjp.expandframework.interfaces.PermissionCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求权限构建者模式封装
 * author : Gimpo create on 2018/6/6 14:50
 * email  : jimbo922@163.com
 */
public class PermissionUtil {

    private Context context;
    public static ArrayList<String> permissions;
    public static PermissionCallback callback;

    public static class Builder {
        private Context context;
        private ArrayList<String> permissions = new ArrayList<>();

        public Builder() {

        }

        public PermissionUtil.Builder context(Context context) {
            this.context = context;
            return this;
        }

        public PermissionUtil.Builder permission(String permission) {
            this.permissions.add(permission);
            return this;
        }

        public PermissionUtil build() {
            PermissionUtil utils = new PermissionUtil();
            if (this.context == null) {
                throw new IllegalArgumentException("context must be not null");
            }
            utils.context = this.context;
            utils.permissions = this.permissions;
            return utils;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public void request(PermissionCallback callback) {
        PermissionUtil.callback = callback;
        Intent intent = new Intent(context, PermissionActivity.class);
        context.startActivity(intent);
    }

    /**
     * 获取权限提示
     *
     * @return
     */
    public static String getNotice(List<String> permissions) {
        StringBuilder sb = new StringBuilder();
        sb.append("您的");
        if (permissions != null && permissions.size() > 0) {
            for (String permission : permissions) {
                sb.append(getPermissionName(permission)).append("、");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("已被禁止，需要去权限管理中打开。");
        return sb.toString();
    }

    /**
     * 权限的描述
     *
     * @param permission
     * @return
     */
    private static String getPermissionName(String permission) {
        switch (permission) {
            case Manifest.permission.READ_CALENDAR:
                return "读日程权限";
            case Manifest.permission.WRITE_CALENDAR:
                return "写日程权限";
            case Manifest.permission.CAMERA:
                return "相机权限";
            case Manifest.permission.READ_CONTACTS:
                return "读通讯录权限";
            case Manifest.permission.WRITE_CONTACTS:
                return "写通讯录权限";
            case Manifest.permission.GET_ACCOUNTS:
                return "获取账户权限";
            case Manifest.permission.ACCESS_FINE_LOCATION:
                return "GPS定位权限";
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                return "网络定位权限";
            case Manifest.permission.RECORD_AUDIO:
                return "录音权限";
            case Manifest.permission.READ_PHONE_STATE:
                return "读取手机信息权限";
            case Manifest.permission.CALL_PHONE:
                return "电话权限";
            case Manifest.permission.READ_CALL_LOG:
                return "读电话记录权限";
            case Manifest.permission.WRITE_CALL_LOG:
                return "写电话记录权限";
            case Manifest.permission.ADD_VOICEMAIL:
                return "添加语音邮件权限";
            case Manifest.permission.USE_SIP:
                return "SIP视频服务权限";
            case Manifest.permission.PROCESS_OUTGOING_CALLS:
                return "监视电话权限";
            case Manifest.permission.BODY_SENSORS:
                return "传感器权限";
            case Manifest.permission.SEND_SMS:
                return "发短信权限";
            case Manifest.permission.RECEIVE_SMS:
                return "收短信权限";
            case Manifest.permission.READ_SMS:
                return "读短信权限";
            case Manifest.permission.RECEIVE_WAP_PUSH:
                return "监测推送权限";
            case Manifest.permission.RECEIVE_MMS:
                return "收彩信权限";
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                return "读外部存储权限";
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                return "存储权限";
        }
        return "未知权限";
    }

    public static void gotoPermission(Context context, String packageName) {
        String brand = Build.BRAND;//手机厂商
        if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), "xiaomi")) {
            PermissionUtil.gotoMiuiPermission(context);//小米
        } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
            PermissionUtil.gotoMeizuPermission(context, packageName);
        } else if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
            PermissionUtil.gotoHuaweiPermission(context);
        } else {
            context.startActivity(PermissionUtil.getAppDetailSettingIntent(context));
        }
    }


    /**
     * 跳转到miui的权限管理页面
     */
    private static void gotoMiuiPermission(Context context) {
        try { // MIUI 8
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", context.getPackageName());
            context.startActivity(localIntent);
        } catch (Exception e) {
            try { // MIUI 5/6/7
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", context.getPackageName());
                context.startActivity(localIntent);
            } catch (Exception e1) { // 否则跳转到应用详情
                context.startActivity(getAppDetailSettingIntent(context));
            }
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private static void gotoMeizuPermission(Context context, String packageName) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", packageName);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            context.startActivity(getAppDetailSettingIntent(context));
        }
    }

    /**
     * 华为的权限管理页面
     */
    private static void gotoHuaweiPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            context.startActivity(getAppDetailSettingIntent(context));
        }

    }

    /**
     * 获取应用详情页面intent（如果找不到要跳转的界面，也可以先把用户引导到系统设置页面）
     */
    private static Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));

        return localIntent;
    }
}
