package com.salmandhariwala.jarvis.events.actions;

import android.content.Context;
import android.content.Intent;

/**
 * Created by salmandhariwala on 28/01/18.
 */

public class ActionUtil {

    public static void startSendSmsAction(Context context, String phone, String message) {

        Intent c = new Intent(context, SmsSendAction.class);
        c.putExtra("msg", message);
        c.putExtra("phone", phone);
        context.startService(c);

    }

    public static void soundProfileChangeAction(Context context, boolean isSilent) {

        Intent c = new Intent(context, ChangeMobileProfileAction.class);
        c.putExtra("isSilent", isSilent);
        context.startService(c);

    }

    public static void ttsAction(Context context, String text) {

        Intent c = new Intent(context, TextToSpeechAction.class);
        c.putExtra("text", text);
        context.startService(c);

    }

}
