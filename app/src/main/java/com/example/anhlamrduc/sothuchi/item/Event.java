package com.example.anhlamrduc.sothuchi.item;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AnhlaMrDuc on 23-Apr-16.
 */
public class Event implements Parcelable {
    private int eventID;
    private String eventName;
    private String startDate;
    private String status;

    public Event() {
    }

    public Event(int eventID, String eventName, String startDate, String trangThai) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.startDate = startDate;
        this.status = trangThai;
    }

    protected Event(Parcel in) {
        eventID = in.readInt();
        eventName = in.readString();
        startDate = in.readString();
        status = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(eventID);
        dest.writeString(eventName);
        dest.writeString(startDate);
        dest.writeString(status);
    }
}
