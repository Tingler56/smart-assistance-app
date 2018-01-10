package com.salmandhariwala.jarvis.trigger.handler;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by salmandhariwala on 04/01/18.
 */

public class LocationTriggerHandler extends IntentService {

    String tag=LocationTriggerHandler.class.getSimpleName();

    public LocationTriggerHandler(){
        super(LocationTriggerHandler.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
