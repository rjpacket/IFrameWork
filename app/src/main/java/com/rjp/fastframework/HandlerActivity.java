package com.rjp.fastframework;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.rjp.expandframework.views.ProgressView;

import java.util.ArrayList;
import java.util.List;

public class HandlerActivity extends Activity{

    private List<String> datas;
    private Context mContext;
    private ProgressView progressView;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        mContext = this;
        ListView listView = findViewById(R.id.list_view);
        datas = new ArrayList<>();

        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736892&di=bca7d2096bedb121cf9e11bc1af65311&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fblog%2F201504%2F05%2F20150405131044_XPjfk.thumb.700_0.jpeg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736892&di=9e65dac808fcfbf4993e73287165b76d&imgtype=0&src=http%3A%2F%2Fimg0.ph.126.net%2FQDC8mjk8s7LQxUwZ0mOaVA%3D%3D%2F1689412810317456108.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736891&di=17382fb6a4087fdb806bfa7d0230111b&imgtype=0&src=http%3A%2F%2Fpic.gooooal.com%2Fimages%2F100381%2F100381157.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736891&di=67042e21af9f285d649091249b0fc04b&imgtype=0&src=http%3A%2F%2Fwww.qiuball.com%2FUploadFiles%2F2015%2F2%2F201502041107006301.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736891&di=01d117096e2c2ab46bd2c79410b3e706&imgtype=0&src=http%3A%2F%2Fi2.hoopchina.com.cn%2Fblogfile%2F201211%2F27%2F135399473182266.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736891&di=d159ff026edfcd5542de6622845b3078&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fwallpaper%2F1208%2F27%2Fc1%2F13220511_1346041622091.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736891&di=cf1e56b4c25bace3531edb0b0e8cdb4c&imgtype=0&src=http%3A%2F%2Fimage.tupian114.com%2F20140606%2F00031208.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736887&di=8d1b22b90990232da5f7811969535b79&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20090307%2FImg262663942.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736887&di=4dd821b79978e670746b2cedffca5bf0&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20111228%2FImg330540970.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736892&di=bca7d2096bedb121cf9e11bc1af65311&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fblog%2F201504%2F05%2F20150405131044_XPjfk.thumb.700_0.jpeg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736892&di=9e65dac808fcfbf4993e73287165b76d&imgtype=0&src=http%3A%2F%2Fimg0.ph.126.net%2FQDC8mjk8s7LQxUwZ0mOaVA%3D%3D%2F1689412810317456108.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736891&di=17382fb6a4087fdb806bfa7d0230111b&imgtype=0&src=http%3A%2F%2Fpic.gooooal.com%2Fimages%2F100381%2F100381157.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736891&di=67042e21af9f285d649091249b0fc04b&imgtype=0&src=http%3A%2F%2Fwww.qiuball.com%2FUploadFiles%2F2015%2F2%2F201502041107006301.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736891&di=01d117096e2c2ab46bd2c79410b3e706&imgtype=0&src=http%3A%2F%2Fi2.hoopchina.com.cn%2Fblogfile%2F201211%2F27%2F135399473182266.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736891&di=d159ff026edfcd5542de6622845b3078&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fwallpaper%2F1208%2F27%2Fc1%2F13220511_1346041622091.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736891&di=cf1e56b4c25bace3531edb0b0e8cdb4c&imgtype=0&src=http%3A%2F%2Fimage.tupian114.com%2F20140606%2F00031208.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736887&di=8d1b22b90990232da5f7811969535b79&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20090307%2FImg262663942.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736892&di=bca7d2096bedb121cf9e11bc1af65311&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fblog%2F201504%2F05%2F20150405131044_XPjfk.thumb.700_0.jpeg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736892&di=9e65dac808fcfbf4993e73287165b76d&imgtype=0&src=http%3A%2F%2Fimg0.ph.126.net%2FQDC8mjk8s7LQxUwZ0mOaVA%3D%3D%2F1689412810317456108.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736891&di=17382fb6a4087fdb806bfa7d0230111b&imgtype=0&src=http%3A%2F%2Fpic.gooooal.com%2Fimages%2F100381%2F100381157.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736891&di=67042e21af9f285d649091249b0fc04b&imgtype=0&src=http%3A%2F%2Fwww.qiuball.com%2FUploadFiles%2F2015%2F2%2F201502041107006301.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736891&di=01d117096e2c2ab46bd2c79410b3e706&imgtype=0&src=http%3A%2F%2Fi2.hoopchina.com.cn%2Fblogfile%2F201211%2F27%2F135399473182266.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736891&di=d159ff026edfcd5542de6622845b3078&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fwallpaper%2F1208%2F27%2Fc1%2F13220511_1346041622091.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736891&di=cf1e56b4c25bace3531edb0b0e8cdb4c&imgtype=0&src=http%3A%2F%2Fimage.tupian114.com%2F20140606%2F00031208.jpg");
        datas.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553158736887&di=8d1b22b90990232da5f7811969535b79&imgtype=0&src=http%3A%2F%2Fphotocdn.sohu.com%2F20090307%2FImg262663942.jpg");

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return datas.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_view, null);
                }
                ImageView ivImageView = convertView.findViewById(R.id.riv_image_view);
//                ImageLoaderUtil.load(mContext, datas.get(position), ivImageView);
                return convertView;
            }
        });

        progressView = findViewById(R.id.progress_view);
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressView.setProgress((float) (progressView.getProgress() + 0.01));
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
