package com.ewaste.garbagecollacter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AwarenessContent extends AppCompatActivity {
TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awareness_content);
        content=findViewById(R.id.cont);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
                content.setText(bundle.getString("content"));
        }
    }
}