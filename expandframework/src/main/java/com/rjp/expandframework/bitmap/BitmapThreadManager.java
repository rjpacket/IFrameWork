package com.rjp.expandframework.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.rjp.expandframework.utils.FileUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author: jinpeng.ren create at 2019/6/11 18:13
 * email: jinpeng.ren@11bee.com
 */
public class BitmapThreadManager {

    public static final String NETWORK_CACHE_FILE_NAME = "network_route.cache";

    private final ExecutorService executorService;
    public Map<String, Bitmap> bitmapCacheMap = new HashMap<>();

    private BitmapThreadManager() {
        executorService = Executors.newCachedThreadPool();
    }

    public void addTask(Runnable task) {
        executorService.execute(task);
    }

    private static BitmapThreadManager instance = null;

    public static BitmapThreadManager getInstance() {
        if (instance == null) {
            synchronized (BitmapThreadManager.class) {
                if (instance == null) {
                    instance = new BitmapThreadManager();
                }
            }
        }
        return instance;
    }

    public Bitmap getCacheBitmap(String url) {
        if (bitmapCacheMap.containsKey(url)) {
            return bitmapCacheMap.get(url);
        }
        return null;
    }

    /**
     * 通过md5码获取图片文件存储位置
     *
     * @param context
     * @param encode
     * @return
     */
    public String getFilePathByMd5(Context context, String encode, String fileName) {
        BufferedReader reader = null;
        try {
            reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(
                                            new File(FileUtil.getCacheImagesPath(context), fileName))));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(encode)) {
                    String[] split = line.split("=");
                    return split[1];
                }
            }
        } catch (Exception e) {

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 通过url获取一张图片
     *
     * @param imageUrl
     * @return
     */
    public Bitmap getBitmapByUrl(String imageUrl) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(imageUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(false);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("charset", "UTF-8");
            if (connection.getResponseCode() == 200) {
                return BitmapFactory.decodeStream(connection.getInputStream());
            }
        } catch (Exception e) {

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    /**
     * 缓存一个图片到内存
     * @param url
     * @param bitmap
     */
    public void put(String url, Bitmap bitmap) {
        bitmapCacheMap.put(url, bitmap);
    }

    /**
     * 保存缓存的路由文件
     * @param context
     * @param fileName
     * @param encode
     * @param bitmapPath
     */
    public void save(Context context, String fileName, String encode, String bitmapPath) {
        BufferedWriter writer = null;
        try {
            writer =
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream(new File(FileUtil.getCacheImagesPath(context), fileName), true)));
            writer.write(String.format("%s=%s\n", encode, bitmapPath));
            writer.flush();
        } catch (Exception e) {

        } finally {
            if(writer != null){
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
