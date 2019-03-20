package com.rjp.fastframework;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HandlerActivity extends Activity{

    private TextView tvMessage;

    private List<String> boxs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);

        tvMessage = findViewById(R.id.tv_message);

        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boxs.add("hello world");
            }
        }.start();

        loop();

    }

    public void loop() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(boxs.size() > 0){
            String remove = boxs.remove(0);
            tvMessage.setText(remove);
        }
    }
}
