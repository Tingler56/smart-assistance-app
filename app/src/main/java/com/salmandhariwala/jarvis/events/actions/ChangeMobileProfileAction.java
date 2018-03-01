package com.salmandhariwala.jarvis.events.actions;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by salmandhariwala on 27/01/18.
 */

public class ChangeMobileProfileAction extends IntentService {

    String tag = ChangeMobileProfileAction.class.getSimpleName();

    public ChangeMobileProfileAction() {
        super(ChangeMobileProfileAction.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        boolean isSilent = intent.getBooleanExtra("isSilent", false);

        AudioManager am;
        am = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);


        if (isSilent) {
            am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            Log.d(tag, "profile set to silent!");

            am.setStreamVolume(
                    AudioManager.STREAM_RING,
                    0,
                    AudioManager.FLAG_SHOW_UI
            );

        } else {
            am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            // Get the ringer maximum volume
            int max_volume_level = am.getStreamMaxVolume(AudioManager.STREAM_RING);

            am.setStreamVolume(
                    AudioManager.STREAM_RING,
                    max_volume_level,
                    AudioManager.FLAG_SHOW_UI
            );

            Log.d(tag, "profile set to normal!");
        }


    }
}
