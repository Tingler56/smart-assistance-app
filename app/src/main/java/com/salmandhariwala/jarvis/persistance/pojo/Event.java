package com.salmandhariwala.jarvis.persistance.pojo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by salmandhariwala on 27/01/18.
 */

@Entity
public class Event {

    @PrimaryKey(autoGenerate = true)
    int id;

    String eventName;

    boolean isEventEnabled;

    boolean locationTriggerEnabled;
    boolean smsTriggerEnabled;
    boolean timeTriggerEnabled;

    String location;
    String smsBodyContent;
    String time;

    // -----

    boolean profileChangeEventEnabled;
    boolean smsSendActionEnabled;
    boolean textToSpeechEnabled;

    boolean silentAction;
    String ttsText;
    String phoneNum;
    String message;

    @Override
    public String toString() {

        return eventName + "_" + isEventEnabled + "_" +
                locationTriggerEnabled + "_" + smsTriggerEnabled + "_" + timeTriggerEnabled + "_" +
                location + "_" + smsBodyContent + "_" + time + "_" + profileChangeEventEnabled + "_" +
                smsSendActionEnabled + "_" + textToSpeechEnabled + "_" + silentAction + "_" + ttsText + "_" +
                phoneNum + "_" + message;

    }

    public String getEventInfo(){

        StringBuilder sb =new StringBuilder();

        sb.append("**** Event : "+eventName+" **** \n");
        sb.append("## Triggers Configured : \n");
        if(isLocationTriggerEnabled()){
            sb.append("- Location trigger on : "+getLocation());
            sb.append("\n");
        }
        if(isSmsTriggerEnabled()){
            sb.append("- Sms trigger enabled with wake text : "+getSmsBodyContent());
            sb.append("\n");
        }
        if(isTimeTriggerEnabled()){
            sb.append("- Time trigger enabled on date : "+getTime());
            sb.append("\n");
        }
        sb.append("## Events configured : \n");
        if(isProfileChangeEventEnabled()){
            sb.append("- Sound profile change");
            sb.append("\n");
        }
        if(isSmsSendActionEnabled()){
            sb.append("- SMS send action enabled");
            sb.append("\n");
        }
        if(isTextToSpeechEnabled()){
            sb.append("- Text to speed enabled");
            sb.append("\n");
        }


        return sb.toString();

    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEventEnabled() {
        return isEventEnabled;
    }

    public void setEventEnabled(boolean eventEnabled) {
        isEventEnabled = eventEnabled;
    }

    public boolean isLocationTriggerEnabled() {
        return locationTriggerEnabled;
    }

    public void setLocationTriggerEnabled(boolean locationTriggerEnabled) {
        this.locationTriggerEnabled = locationTriggerEnabled;
    }

    public boolean isSmsTriggerEnabled() {
        return smsTriggerEnabled;
    }

    public void setSmsTriggerEnabled(boolean smsTriggerEnabled) {
        this.smsTriggerEnabled = smsTriggerEnabled;
    }

    public boolean isTimeTriggerEnabled() {
        return timeTriggerEnabled;
    }

    public void setTimeTriggerEnabled(boolean timeTriggerEnabled) {
        this.timeTriggerEnabled = timeTriggerEnabled;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSmsBodyContent() {
        return smsBodyContent;
    }

    public void setSmsBodyContent(String smsBodyContent) {
        this.smsBodyContent = smsBodyContent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isProfileChangeEventEnabled() {
        return profileChangeEventEnabled;
    }

    public void setProfileChangeEventEnabled(boolean profileChangeEventEnabled) {
        this.profileChangeEventEnabled = profileChangeEventEnabled;
    }

    public boolean isSmsSendActionEnabled() {
        return smsSendActionEnabled;
    }

    public void setSmsSendActionEnabled(boolean smsSendActionEnabled) {
        this.smsSendActionEnabled = smsSendActionEnabled;
    }

    public boolean isTextToSpeechEnabled() {
        return textToSpeechEnabled;
    }

    public void setTextToSpeechEnabled(boolean textToSpeechEnabled) {
        this.textToSpeechEnabled = textToSpeechEnabled;
    }

    public boolean isSilentAction() {
        return silentAction;
    }

    public void setSilentAction(boolean silentAction) {
        this.silentAction = silentAction;
    }

    public String getTtsText() {
        return ttsText;
    }

    public void setTtsText(String ttsText) {
        this.ttsText = ttsText;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
