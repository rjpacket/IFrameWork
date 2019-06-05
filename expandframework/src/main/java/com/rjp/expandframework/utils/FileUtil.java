package com.rjp.expandframework.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import com.rjp.expandframework.BuildConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * author : Gimpo create on 2018/12/10 16:01
 * email  : jimbo922@163.com
 */
public class FileUtil {
    public static final String IMAGES_DIR = "images";
    public static final String LOGS_DIR = "logs";
    public static final String APKS_DIR = "apks";

    /**
     * 是否挂载了sd卡
     *
     * @return
     */
    public static boolean isMounted() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 创建文件
     *
     * @param file
     * @return
     */
    public static File createNewFile(File file) {
        if (file == null) {
            return null;
        }
        try {
            if (file.exists()) {
                return file;
            }
            File dir = file.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            return null;
        }
        return file;
    }

    /**
     * 创建文件
     *
     * @param path
     * @return
     */
    public static File createNewFile(String path) {
        File file = new File(path);
        return createNewFile(file);
    }

    /**
     * 创建文件夹
     *
     * @param dir
     * @return
     */
    public static File createNewDir(File dir) {
        if (dir == null) {
            return null;
        }
        try {
            if (dir.exists()) {
                return dir;
            } else {
                dir.mkdirs();
            }
        } catch (Exception e) {
            return null;
        }
        return dir;
    }

    /**
     * 创建文件夹
     *
     * @param dirPath
     * @return
     */
    public static File createNewDir(String dirPath) {
        File dir = new File(dirPath);
        return createNewDir(dir);
    }

    /**
     * 删除文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file == null) {
            return;
        }
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File files[] = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFile(files[i]);
            }
            file.delete();
        }
    }

    /**
     * 删除文件
     *
     * @param path
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        deleteFile(file);
    }

    /**
     * 获取存储的cache目录 非重要数据 重要数据存储在getExternalFileDir
     */
    public static File getStorageCacheDirectory(Context context) {
        if (isMounted()) {
            return context.getExternalCacheDir();
        } else {
            return context.getCacheDir();
        }
    }

    /**
     * 获取存储app图片的文件路径
     *
     * @param context
     * @return
     */
    public static String getAppImagesPath(Context context) {
        File file = createNewDir(new File(getStorageCacheDirectory(context), IMAGES_DIR));
        return file == null ? "" : file.getAbsolutePath();
    }

    /**
     * 获取存储app Log的文件路径
     *
     * @param context
     * @return
     */
    public static String getAppLogsPath(Context context) {
        File file = createNewDir(new File(FileUtil.getStorageCacheDirectory(context), LOGS_DIR));
        Log.d("===getAppLogsPath===>", file.getAbsolutePath());
        return file == null ? "" : file.getAbsolutePath();
    }

    /**
     * 获取存储app apk的文件路径
     *
     * @param context
     * @return
     */
    public static String getAppApksPath(Context context) {
        File file = createNewDir(new File(getStorageCacheDirectory(context), APKS_DIR));
        return file == null ? "" : file.getAbsolutePath();
    }

    /*---------------------------------------------以下为工具-------------------------------------------------*/

    public static String copyAssetsToSD(Context context, String fileName) {
        FileOutputStream fos = null;
        InputStream inputStream = null;
        try {
            String appApksPath = getAppApksPath(context);
            if (!TextUtils.isEmpty(appApksPath)) {
                File outFile = new File(appApksPath, fileName);
                fos = new FileOutputStream(outFile);

                inputStream = context.getAssets().open(fileName);
                byte[] buffer = new byte[inputStream.available()];
                int byteCount;
                while ((byteCount = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteCount);
                }
                fos.flush();
                return outFile.getAbsolutePath();
            }
        } catch (Exception e) {

        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 保存一张图片
     *
     * @param context
     * @param bitmap
     * @param imageName
     */
    public static void saveBitmap(Context context, Bitmap bitmap, String imageName) {
        FileOutputStream fos = null;
        String mFilePath = FileUtil.getAppImagesPath(context) + File.separator + imageName;
        File tempFile = new File(mFilePath);
        try {
            fos = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 一个版本只有一个更新文件
     *
     * @param context
     * @return
     */
    public static String getAutoUpdateApkPath(Context context) {
        return getAppApksPath(context) + File.separator + "AutoUpdate" + BuildConfig.VERSION_NAME + ".apk";
    }

    /**
     * 获取文件的uri
     *
     * @param file
     * @return
     */
    public static Uri file2Uri(@NonNull final File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authority = AppUtil.getApp().getPackageName() + ".utilcode.provider";
            return FileProvider.getUriForFile(AppUtil.getApp(), authority, file);
        } else {
            return Uri.fromFile(file);
        }
    }

    public static String getFilePathByUri(Context context, Uri uri) {
        String path = null;
        // 以 file:// 开头的
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            path = uri.getPath();
            return path;
        }
        // 以 content:// 开头的，比如 content://media/extenral/images/media/17766
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (columnIndex > -1) {
                        path = cursor.getString(columnIndex);
                    }
                }
                cursor.close();
            }
            return path;
        }
        // 4.4及之后的 是以 content:// 开头的，比如 content://com.android.providers.media.documents/document/image%3A235700
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    // ExternalStorageProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        path = Environment.getExternalStorageDirectory() + "/" + split[1];
                        return path;
                    }
                } else if (isDownloadsDocument(uri)) {
                    // DownloadsProvider
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(id));
                    path = getDataColumn(context, contentUri, null, null);
                    return path;
                } else if (isMediaDocument(uri)) {
                    // MediaProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};
                    path = getDataColumn(context, contentUri, selection, selectionArgs);
                    return path;
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                // Return the remote address
                if (isGooglePhotosUri(uri)) return uri.getLastPathSegment();
                return getDataColumn(context, uri, null, null);
            }
        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String openAssets(Context context, String fileName){
        InputStream inputStream = null;
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            inputStream = context.getAssets().open(fileName);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
