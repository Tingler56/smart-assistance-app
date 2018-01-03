package com.salmandhariwala.jarvis;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean permission = checkAndRequestPermissions();
        Log.d("permission", permission + "");

    }

    private boolean checkAndRequestPermissions() {

        int receiveSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS);

        if (receiveSMS != PERMISSION_GRANTED) {
            return true;
        }

        return false;
    }


}
