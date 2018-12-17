package com.rjp.fastframework.live;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.rjp.fastframework.R;

public class LiveStartActivity extends Activity {

    private EditText etRoomUrl;
    private Context mContext;

    public static void trendTo(Context mContext) {
        Intent intent = new Intent(mContext, LiveStartActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_start);

        mContext = this;

        etRoomUrl = findViewById(R.id.et_room_url);

        findViewById(R.id.btn_start_room).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveDetailActivity.trendTo(mContext, etRoomUrl.getText().toString().trim());
            }
        });
    }
}
