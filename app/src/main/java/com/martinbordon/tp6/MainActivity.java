package com.martinbordon.tp6;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.R
                && checkSelfPermission(Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
             requestPermissions(new String[]{Manifest.permission.READ_SMS}, 1000);
        }
    intent = new Intent(this,servicio.class);;
    }

    public void iniciar(View view) {
        this.startService(intent);
    }
    public void detener(View view) {
        this.stopService(intent);
    }

    //46:11
}