package com.salmandhariwala.jarvis.events.handler;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.salmandhariwala.jarvis.persistance.database.SampleDatabase;
import com.salmandhariwala.jarvis.persistance.pojo.Event;

import java.util.List;

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

        String sms = intent.getStringExtra("sms");

        Log.d(tag, "Sms trigger handler called ! ( " + sms + " )");

        SampleDatabase sampleDatabase = Room.databaseBuilder(getApplicationContext(),
                SampleDatabase.class, "sample-db").allowMainThreadQueries().build();


        List<Event> events = sampleDatabase.daoAccess().fetchAllData();

        for (Event event : events) {

            if (event.isEventEnabled()) {

                if (event.isSmsTriggerEnabled()) {

                    if (sms.contains(event.getSmsBodyContent())) {

                        HandleUtil.handle(getApplicationContext(), event, sampleDatabase);

                    }

                }

            }

        }

    }
}
