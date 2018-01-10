package com.salmandhariwala.jarvis.trigger.handler;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by salmandhariwala on 04/01/18.
 */

public class SmsTriggerHandler extends IntentService {

    String tag = SmsTriggerHandler.class.getSimpleName();

    public SmsTriggerHandler() {
        super(SmsTriggerHandler.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d(tag, "sms service started!!!!!!!");

        AudioManager am;
        am = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);

        String sms = intent.getStringExtra("sms");

        if(sms.contains("jarvis silent")){
            am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            Log.d(tag, "profile set to silent!");
        }else if(sms.contains("jarvis normal")){
            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            Log.d(tag, "profile set to normal!");
        }

    }
}
