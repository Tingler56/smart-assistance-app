package com.salmandhariwala.jarvis.persistance.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.salmandhariwala.jarvis.persistance.dao.DaoAccess;
import com.salmandhariwala.jarvis.persistance.pojo.Event;

/**
 * Created by salmandhariwala on 27/01/18.
 */

@Database(entities = {Event.class}, version = 1)
public abstract class SampleDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();
}
