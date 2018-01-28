package com.salmandhariwala.jarvis.events.trigger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.salmandhariwala.jarvis.events.handler.SmsTriggerHandler;

public class SMSTrigger extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get Bundle object contained in the SMS intent passed in
        Bundle bundle = intent.getExtras();
        SmsMessage[] smsm = null;
        String sms_str = "";

        if (bundle != null) {
            // Get the SMS message
            Object[] pdus = (Object[]) bundle.get("pdus");
            smsm = new SmsMessage[pdus.length];
            for (int i = 0; i < smsm.length; i++) {
                smsm[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                sms_str += "\r\nMessage: ";
                sms_str += smsm[i].getMessageBody().toString();
                sms_str += "\r\n";

                String Sender = smsm[i].getOriginatingAddress();
                //Check here sender is yours
                Intent smsIntent = new Intent("otp");
                smsIntent.putExtra("message", sms_str);


                handleSMS(context, sms_str);

            }
        }
    }


    private void handleSMS(Context context, String message) {

//        Log.d("jarvis_sms_hanler", message);

        Intent c = new Intent(context, SmsTriggerHandler.class);
        c.putExtra("sms", message);

        context.startService(c);

    }
}
