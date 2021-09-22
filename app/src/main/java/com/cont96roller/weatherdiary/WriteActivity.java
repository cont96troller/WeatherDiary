package com.cont96roller.weatherdiary;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WriteActivity extends AppCompatActivity {

    private Button mButton2;
    private Button mButton3;
    private Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        mContext = this;

        Intent intent = null;
        intent = getIntent();
        String value = null;
        value = intent.getStringExtra("key");
        Toast toast = Toast.makeText(mContext, value, Toast.LENGTH_SHORT);
        toast.show();


        mButton2 = findViewById(R.id.btn_ok);
        mButton2.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }));

        mButton3 = findViewById(R.id.btn_back);
        mButton3.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }));

    }
}
