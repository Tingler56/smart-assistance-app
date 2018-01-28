package com.salmandhariwala.jarvis.events.actions;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * Created by salmandhariwala on 27/01/18.
 */

public class SmsSendAction extends IntentService {

    String tag= SmsSendAction.class.getSimpleName();

    public SmsSendAction() {
        super(SmsSendAction.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d(tag ,"SmsSendAction called . . .");

        String msg = intent.getStringExtra("msg");
        String phone = intent.getStringExtra("phone");

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone, null, msg, null, null);

    }
}
