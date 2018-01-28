package com.salmandhariwala.jarvis.events.trigger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.salmandhariwala.jarvis.events.handler.TimeTriggerHandler;

import java.util.Date;

/**
 * Created by salmandhariwala on 27/01/18.
 */

public class TimeTrigger extends BroadcastReceiver{

    public static final String tag = "TimeTrigger";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(tag,"broadcast received in TimeTrigger");


        Intent c=new Intent(context,TimeTriggerHandler.class);
        c.putExtra("currTime",new Date().getTime());
        context.startService(c);




    }
}
