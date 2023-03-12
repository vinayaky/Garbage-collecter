package com.ewaste.garbagecollacter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class detailActivity extends AppCompatActivity {
    TextView name , work, add ,number;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        image = findViewById(R.id.detailImage);
        name = findViewById(R.id.detailName);
        work = findViewById(R.id.detailWork);
        add =  findViewById(R.id.detailAdd);
        number=findViewById(R.id.detailNumber);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            name.setText(bundle.getString("name"));
            work.setText(bundle.getString("work"));
            add.setText(bundle.getString("add"));
            number.setText(bundle.getString("number"));

            Glide.with(this).load(bundle.getString("image")).into(image);

        }
    }
}