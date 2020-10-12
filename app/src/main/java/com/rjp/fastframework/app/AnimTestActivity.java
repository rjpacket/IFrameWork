package com.rjp.fastframework.app;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import com.rjp.fastframework.R;
import com.rjp.fastframework.views.CardContainerView;
import com.rjp.fastframework.views.CardView;
import com.rjp.fastframework.views.DefaultCardView;

public class AnimTestActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_test);

        context = this;

        CardContainerView cardContainerView = findViewById(R.id.card_container_view);

        for (int i = 0; i < 5; i++) {
            CardView cardView = new CardView(this);
            cardContainerView.addCard(cardView);
        }
    }
}
