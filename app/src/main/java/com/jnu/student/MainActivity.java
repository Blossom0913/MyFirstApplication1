package com.jnu.student;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        TextView textViewHelloWorld = findViewById(R.id.text_view_hello_world);
        String helloAndroid = getResources().getString(R.string.hello_android);
        textViewHelloWorld.setText(helloAndroid);
    }
}