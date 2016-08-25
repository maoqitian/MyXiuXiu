package com.mao.myxiuxiu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

   private XiuYiXiu xiuxiu;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xiuxiu= (XiuYiXiu) findViewById(R.id.xiuxiu);
        xiuxiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xiuxiu.onStart();
            }
        });
    }
}
