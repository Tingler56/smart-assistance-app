package com.salmandhariwala.jarvis;

import android.Manifest;
import android.app.NotificationManager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.salmandhariwala.jarvis.activities.CreateEventActivity;
import com.salmandhariwala.jarvis.events.trigger.LocationTrigger;
import com.salmandhariwala.jarvis.events.trigger.TimeTrigger;
import com.salmandhariwala.jarvis.persistance.database.SampleDatabase;
import com.salmandhariwala.jarvis.persistance.pojo.Event;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    SampleDatabase sampleDatabase;
    Button createEventButton;
    LinearLayout eventEntriesLayout;

    int enabledColor = Color.parseColor("#F5F5F5");
    int disabledColor = Color.parseColor("#C0C0C0");

    String tag = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUiComponents();

        setupPermissions();

        boostrapServices();

        setupDatabase();

        displayDbEntries();

    }

    private void displayDbEntries() {

        eventEntriesLayout.removeAllViewsInLayout();

        List<Event> events = sampleDatabase.daoAccess().fetchAllData();

        for(Event event:events){
            CardView card = getCard(event);
            eventEntriesLayout.addView(card);
        }


    }

    private CardView getCard(Event event){
        // Initialize a new CardView
        CardView card = new CardView(getApplicationContext());

        // Set the CardView layoutParams
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(30, 20, 30, 30);

        card.setLayoutParams(params);

        // Set CardView corner radius
        card.setRadius(9);

        // Set cardView content padding
        card.setContentPadding(15, 15, 15, 15);

        // Set a background color for CardView

        if(event.isEventEnabled()){
            card.setCardBackgroundColor(enabledColor);
        }else{
            card.setBackgroundColor(disabledColor);
        }

        // Set the CardView maximum elevation
        card.setMaxCardElevation(15);

        // Set CardView elevation
        card.setCardElevation(9);

        // Initialize a new TextView to put in CardView
        TextView tv = new TextView(getApplicationContext());
        tv.setLayoutParams(params);
        tv.setText(event.getEventInfo());
        tv.setTextColor(Color.RED);

        // Put the TextView in CardView
        card.addView(tv);

        View view =new View(getApplicationContext());
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,4));
        card.addView(view);

        card.setId(event.getId());
        card.setOnClickListener(this);

        card.setOnLongClickListener(this);

        return card;
    }

    private void initUiComponents() {

        createEventButton = findViewById(R.id.create_event);

        createEventButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), CreateEventActivity.class);
                startActivity(i);
            }
        });

        eventEntriesLayout = findViewById(R.id.event_entries_layout);

    }

    private void setupDatabase() {

        sampleDatabase = Room.databaseBuilder(getApplicationContext(),
                SampleDatabase.class, "sample-db").allowMainThreadQueries().build();

    }

    private void setupPermissions() {
        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(intent);
        }

        boolean permission = checkAndRequestPermissions();
        Log.d("permission", permission + "");


    }

    private void boostrapServices(){

        registerTimeBroadcastReceiver();
        registerLocationListener();

    }

    private void registerLocationListener() {

        Intent i = new Intent(this, LocationTrigger.class);

        getApplicationContext().startService(i);

    }

    private void registerTimeBroadcastReceiver() {

        getApplicationContext().registerReceiver(new TimeTrigger(), new IntentFilter(Intent.ACTION_TIME_TICK));


    }

    private boolean checkAndRequestPermissions() {

        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int receiveSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int readSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        int readGPS = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int readGps2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        int sendSms = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (readGPS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (readGps2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (sendSms != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        displayDbEntries();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        displayDbEntries();
        super.onRestart();
    }

    @Override
    public void onClick(View view) {

        int _id = view.getId();

        Log.d(tag,"card clicked : "+_id);

        processToggle(view);

    }

    private void processToggle(View view) {

        int _id = view.getId();

        Event event = sampleDatabase.daoAccess().getSingleRecord(_id);

        if(event.isEventEnabled()){
            event.setEventEnabled(false);
            view.setBackgroundColor(disabledColor);
            sampleDatabase.daoAccess().updateRecord(event);
            Toast.makeText(getApplicationContext(),"Event with id : "+_id+"is DISABLED",Toast.LENGTH_SHORT).show();
        }else{
            event.setEventEnabled(true);
            view.setBackgroundColor(enabledColor);
            sampleDatabase.daoAccess().updateRecord(event);
            Toast.makeText(getApplicationContext(),"Event with id : "+_id+"is ENABLED",Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onLongClick(View view) {

        int _id = view.getId();

        Toast.makeText(getApplicationContext(),"Event with id "+_id+" is DELETED!!",Toast.LENGTH_SHORT).show();

        Event event = sampleDatabase.daoAccess().getSingleRecord(_id);
        sampleDatabase.daoAccess().deleteRecord(event);

        eventEntriesLayout.removeView(view);

        return true;
    }
}
