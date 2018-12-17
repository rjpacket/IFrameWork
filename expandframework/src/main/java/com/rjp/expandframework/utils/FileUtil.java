package com.rjp.expandframework.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * author : Gimpo create on 2018/12/10 16:01
 * email  : jimbo922@163.com
 */
public class FileUtil {
    public static final String IMAGES_DIR = "images";

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

    /*---------------------------------------------以下为工具-------------------------------------------------*/

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
}
