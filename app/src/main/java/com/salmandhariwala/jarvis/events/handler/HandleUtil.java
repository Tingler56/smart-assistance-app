package com.salmandhariwala.jarvis.events.handler;

import android.content.Context;
import android.util.Log;

import com.salmandhariwala.jarvis.events.actions.ActionUtil;
import com.salmandhariwala.jarvis.persistance.database.SampleDatabase;
import com.salmandhariwala.jarvis.persistance.pojo.Event;

/**
 * Created by salmandhariwala on 28/01/18.
 */

public class HandleUtil {


    private static String tag = HandleUtil.class.getSimpleName();

    public static void handle(Context context, Event event, SampleDatabase sampleDatabase){


        Log.d(tag,"trigger matched for event id "+event.getId());

        if(event.isSmsSendActionEnabled()){


            String phone = event.getPhoneNum();
            String message = event.getMessage();

            ActionUtil.startSendSmsAction(context,phone,message);


        }

        if(event.isProfileChangeEventEnabled()){

            boolean isSilent =  event.isSilentAction();

            ActionUtil.soundProfileChangeAction(context,isSilent);

        }

        if(event.isTextToSpeechEnabled()){

            String ttsText =  event.getTtsText();

            ActionUtil.ttsAction(context,ttsText);

        }

        event.setEventEnabled(false);
        sampleDatabase.daoAccess().updateRecord(event);

//        sampleDatabase.daoAccess().deleteRecord(event);

    }

}
