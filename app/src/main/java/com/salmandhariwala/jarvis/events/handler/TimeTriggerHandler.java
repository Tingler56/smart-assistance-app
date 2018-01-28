package com.salmandhariwala.jarvis.events.handler;

import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.salmandhariwala.jarvis.persistance.database.SampleDatabase;
import com.salmandhariwala.jarvis.persistance.pojo.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by salmandhariwala on 27/01/18.
 */

public class TimeTriggerHandler extends IntentService{


    public static final String tag = "TimeTriggerHandler";

    public TimeTriggerHandler() {
        super(TimeTriggerHandler.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        long currTime = intent.getLongExtra("currTime",0);

        Date dt =new Date(currTime);
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd HH:mm");

        String dtString = sdf.format(dt);



        Log.d(tag, "Time trigger handler called ! ( "+dtString+" )");

        SampleDatabase sampleDatabase = Room.databaseBuilder(getApplicationContext(),
                SampleDatabase.class, "sample-db").allowMainThreadQueries().build();



        List<Event> events = sampleDatabase.daoAccess().fetchAllData();

        for (Event event : events) {

            if (event.isEventEnabled()) {

                if (event.isTimeTriggerEnabled()) {

                    if (dtString.equals(event.getTime())) {

                        HandleUtil.handle(getApplicationContext(), event, sampleDatabase);

                    }

                }

            }

        }


    }
}
