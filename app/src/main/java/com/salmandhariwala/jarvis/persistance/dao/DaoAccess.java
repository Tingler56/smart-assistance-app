package com.salmandhariwala.jarvis.persistance.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.salmandhariwala.jarvis.persistance.pojo.Event;

import java.util.List;

/**
 * Created by salmandhariwala on 27/01/18.
 */

@Dao
public interface DaoAccess {


    @Insert
    void insertOnlySingleRecord(Event event);

    @Query("SELECT * FROM Event")
    List<Event> fetchAllData();

    @Query("SELECT * FROM Event WHERE id =:id")
    Event getSingleRecord(int id);

    @Update
    void updateRecord(Event event);

    @Delete
    void deleteRecord(Event event);

}