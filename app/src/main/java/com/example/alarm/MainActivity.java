package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler m = new Handler();
        m.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent a= new Intent(MainActivity.this,firstpage.class);
                startActivity(a);
               finish();
            }
        },4150);
    }
}