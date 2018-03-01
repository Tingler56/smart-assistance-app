package com.salmandhariwala.jarvis.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.salmandhariwala.jarvis.R;
import com.salmandhariwala.jarvis.persistance.database.SampleDatabase;
import com.salmandhariwala.jarvis.persistance.pojo.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    Button openMap;

    String tag = CreateEventActivity.class.getSimpleName();

    SampleDatabase sampleDatabase;

    private int PLACE_PICKER_REQUEST = 1;

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        findComponents();

        setupDatabase();

    }

    private void openMapsForLocation() throws Exception {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder().setLatLngBounds(new LatLngBounds(new LatLng(19.1,72.9),new LatLng(19.1,72.9)));
        startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
//                String toastMsg = String.format("Place: %s", place.getLatLng().latitude);
//                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                locationTriggerLocationEdittext.setText(place.getLatLng().latitude+","+place.getLatLng().longitude);
            }
        }
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
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event event = getEventObject();

                Log.d(tag, event.toString());


                sampleDatabase.daoAccess().insertOnlySingleRecord(event);

                List<Event> data = sampleDatabase.daoAccess().fetchAllData();

                Log.d(tag, "size of events in database is " + data.size());

                Toast.makeText(getApplicationContext(), "Event created !!", Toast.LENGTH_LONG).show();

                finish();
            }
        });

        openMap = findViewById(R.id.maps_open);

        eventNameEditText = findViewById(R.id.event_name_edittext);

        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    openMapsForLocation();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnDatePicker = (Button) findViewById(R.id.btn_date);
        btnTimePicker = (Button) findViewById(R.id.btn_time);
        txtDate = (EditText) findViewById(R.id.in_date);
        txtTime = (EditText) findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);

                            SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd");
                            txtDate.setText(sdf.format(calendar.getTime()));

                            updateTime();
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                            calendar.set(Calendar.MINUTE,minute);

                            SimpleDateFormat sdf =new SimpleDateFormat("HH:mm");
                            txtTime.setText(sdf.format(calendar.getTime()));

                            updateTime();
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    private void updateTime(){
        timeTriggertimeEditText.setText(txtDate.getText().toString()+" "+txtTime.getText().toString());
    }
}
