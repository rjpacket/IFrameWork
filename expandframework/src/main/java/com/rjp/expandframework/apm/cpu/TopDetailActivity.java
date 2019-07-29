package com.rjp.expandframework.apm.cpu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.rjp.expandframework.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopDetailActivity extends AppCompatActivity {

    private TextView textView_title;
    ArrayList<String> cpuList,rssList,vssList = new ArrayList<String>();
    String title;
    ListView listView_detail;
    List<Map<String,String>> mapList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cpu_top);
        textView_title=(TextView) findViewById(R.id.monitor_title);
        listView_detail=(ListView) findViewById(R.id.listview_monitor);

        cpuList = getIntent().getStringArrayListExtra("cpu");
        vssList = getIntent().getStringArrayListExtra("vss");
        rssList = getIntent().getStringArrayListExtra("rss");
        // title=getIntent().getStringExtra("title");
        String times=getIntent().getStringExtra("times");
        String totalTime=getIntent().getStringExtra("totalTime");
        textView_title.setText("监控总时间为"+totalTime+"分钟,监控间隔时间为"+times+"S");


        for(int i=0;i<vssList.size();i++){
            Map<String, String> items=new HashMap<String,String>();
            if(i==0){
                items.put("cpuinfo","CPU            ");
                items.put("rssinfo","RSS                     ");
                items.put("vssinfo","VSS            ");
            }
            else{
                items.put("cpuinfo",cpuList.get(i)+"%            ");
                items.put("rssinfo",rssList.get(i)+"K            ");
                items.put("vssinfo",vssList.get(i)+"K            ");
            }

            mapList.add(items);
        }
        SimpleAdapter simplead = new SimpleAdapter(this, mapList,
                R.layout.monitor_item, new String[] { "cpuinfo", "rssinfo","vssinfo"},
                new int[] {R.id.monitor_cpuitem,R.id.monitor_rssitem,R.id.monitor_vssitem});
        listView_detail.setAdapter(simplead);


    }
}