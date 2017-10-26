package com.halftextview.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.zhs.halftext.HalfTextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HalfTextView tv = (HalfTextView) findViewById(R.id.myview);
        tv.reSetText(getResources().getString(R.string.text_content));
    }
}
