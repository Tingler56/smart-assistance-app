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

public class LocationTriggerHandler extends IntentService {

    String tag=LocationTriggerHandler.class.getSimpleName();

    public LocationTriggerHandler(){
        super(LocationTriggerHandler.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        double lat = intent.getDoubleExtra("lat",0.0);
        double lng = intent.getDoubleExtra("lng",0.0);

        Log.d(tag, "Location trigger handler called ! ( "+lat+" , "+lng+" ) ");

        SampleDatabase sampleDatabase = Room.databaseBuilder(getApplicationContext(),
                SampleDatabase.class, "sample-db").allowMainThreadQueries().build();



        List<Event> events = sampleDatabase.daoAccess().fetchAllData();

        for (Event event : events) {

            if (event.isEventEnabled()) {

                if (event.isLocationTriggerEnabled()) {

                    double latEvent = Double.parseDouble(event.getLocation().split(",")[0]);
                    double lngEvent = Double.parseDouble(event.getLocation().split(",")[1]);

                    double dist = getHaversineDistance(latEvent,lngEvent,lat,lng);

                    if (dist<1) {

                        Log.d(tag, "location tigger called for event "+event.getId());

                        HandleUtil.handle(getApplicationContext(), event, sampleDatabase);

                    }else{
                        Log.d(tag, "location tigger | but dist is more "+dist);
                    }

                }

            }

        }


    }

    private static final int EARTH_RADIUS = 6371;

    public static double getHaversineDistance(double lat1, double lng1, double lat2, double lng2) {

        // yoo
        double dLat = Math.toRadians((lat2 - lat1));
        double dLong = Math.toRadians((lng2 - lng1));

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = haversin(dLat) + Math.cos(lat1) * Math.cos(lat2) * haversin(dLong);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    public static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
