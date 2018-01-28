package com.salmandhariwala.jarvis.activities;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.salmandhariwala.jarvis.R;
import com.salmandhariwala.jarvis.persistance.database.SampleDatabase;
import com.salmandhariwala.jarvis.persistance.pojo.Event;

import java.util.List;


public class CreateEventActivity extends Activity implements View.OnClickListener {

    CheckBox locationTriggerCheckBox;
    CheckBox smsTriggerCheckBox;
    CheckBox timeTriggerCheckbox;

    EditText locationTriggerLocationEdittext;
    EditText smsTriggerSmsBodyContentEditText;
    EditText timeTriggertimeEditText;

    CheckBox profileChangeEventCheckbox;
    CheckBox smsSendEventCheckbox;
    CheckBox textToSpeechEventCheckbox;

    CheckBox setToSilent;

    EditText phoneNumberEditText;
    EditText sendSmsBodyEditText;

    EditText ttsTextEdittext;

    Button submitButton;
    EditText eventNameEditText;

    String tag = CreateEventActivity.class.getSimpleName();

    SampleDatabase sampleDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        findComponents();

        setupDatabase();

    }

    private void setupDatabase() {

        sampleDatabase = Room.databaseBuilder(getApplicationContext(),
                SampleDatabase.class, "sample-db").allowMainThreadQueries().build();

    }

    private void findComponents() {

        locationTriggerCheckBox = findViewById(R.id.location_trigger_checkbox);
        smsTriggerCheckBox = findViewById(R.id.sms_trigger_checkbox);
        timeTriggerCheckbox = findViewById(R.id.time_trigger_checkbox);

        locationTriggerLocationEdittext = findViewById(R.id.event_lat_lng_edittext);
        smsTriggerSmsBodyContentEditText = findViewById(R.id.sms_trigger_edittext);
        timeTriggertimeEditText = findViewById(R.id.time_trigger_edittext);

        profileChangeEventCheckbox = findViewById(R.id.profile_change_event_enabled);
        smsSendEventCheckbox = findViewById(R.id.send_sms_event_enabled);
        textToSpeechEventCheckbox = findViewById(R.id.tts_event_enabled);

        setToSilent = findViewById(R.id.profile_change_event_input);
        phoneNumberEditText = findViewById(R.id.phone_number_edittext);
        sendSmsBodyEditText = findViewById(R.id.sms_message_event_edittext);
        ttsTextEdittext = findViewById(R.id.tts_event_message_edittext);

        submitButton = findViewById(R.id.submit_event);
        submitButton.setOnClickListener(this);

        eventNameEditText = findViewById(R.id.event_name_edittext);

    }

    @Override
    public void onClick(View view) {

        Event event = getEventObject();

        Log.d(tag,event.toString());


        sampleDatabase.daoAccess().insertOnlySingleRecord(event);

        List<Event> data = sampleDatabase.daoAccess().fetchAllData();

        Log.d(tag,"size of events in database is "+data.size());

        Toast.makeText(getApplicationContext(),"Event created !!",Toast.LENGTH_LONG).show();


        finish();



    }

    private Event getEventObject() {

        Event event = new Event();

        event.setEventEnabled(true);
        event.setEventName(eventNameEditText.getText().toString());

        event.setLocationTriggerEnabled(locationTriggerCheckBox.isChecked());
        event.setSmsTriggerEnabled(smsTriggerCheckBox.isChecked());
        event.setTimeTriggerEnabled(timeTriggerCheckbox.isChecked());

        event.setLocation(locationTriggerLocationEdittext.getText().toString());
        event.setSmsBodyContent(smsTriggerSmsBodyContentEditText.getText().toString());
        event.setTime(timeTriggertimeEditText.getText().toString());

        event.setProfileChangeEventEnabled(profileChangeEventCheckbox.isChecked());
        event.setSmsSendActionEnabled(smsSendEventCheckbox.isChecked());
        event.setTextToSpeechEnabled(textToSpeechEventCheckbox.isChecked());

        event.setSilentAction(setToSilent.isChecked());
        event.setTtsText(ttsTextEdittext.getText().toString());
        event.setPhoneNum(phoneNumberEditText.getText().toString());
        event.setMessage(sendSmsBodyEditText.getText().toString());

        return event;

    }
}
