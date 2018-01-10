package com.salmandhariwala.jarvis.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by salmandhariwala on 10/01/18.
 */

public class SmsHandlerActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        String sms = intent.getStringExtra("sms");
    }
}
