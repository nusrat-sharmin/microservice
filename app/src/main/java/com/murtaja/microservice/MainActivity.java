package com.murtaja.microservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.murtaja.microservice.video_stream.VideoHome;
import com.murtaja.microservice.video_stream.VideoRegistration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoRiderApps(View view) {
    }

    public void gotoPizzaApps(View view) {
    }

    public void goToVideoStreaming(View view) {
        startActivity(new Intent(MainActivity.this, VideoHome.class));
        finish();
    }

}
