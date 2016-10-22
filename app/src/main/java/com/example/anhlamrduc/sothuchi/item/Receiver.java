package com.example.anhlamrduc.sothuchi.item;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AnhlaMrDuc on 21-Apr-16.
 */
public class Receiver implements Parcelable {

    private int receiverID;
    private String receiverName;

    public static final Creator<Receiver> CREATOR = new Creator<Receiver>() {
        @Override
        public Receiver createFromParcel(Parcel in) {
            return new Receiver(in);
        }

        @Override
        public Receiver[] newArray(int size) {
            return new Receiver[size];
        }
    };

    public Receiver() {
    }

    public Receiver(int receiverID) {
        this.receiverID = receiverID;
    }

    public Receiver(int receiverID, String receiverName) {
        this.receiverID = receiverID;
        this.receiverName = receiverName;
    }

    protected Receiver(Parcel in) {
        receiverID = in.readInt();
        receiverName = in.readString();
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(receiverID);
        dest.writeString(receiverName);
    }
}
