package com.czq.taobaomain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.czq.taobaomain.widget.CustomPullUpLoadMore;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      final CustomPullUpLoadMore cplm= (CustomPullUpLoadMore) findViewById(R.id.cplm);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cplm.scrollToTop();
            }
        });
    }
}
