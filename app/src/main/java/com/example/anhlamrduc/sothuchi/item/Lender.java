package com.example.anhlamrduc.sothuchi.item;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AnhlaMrDuc on 21-Apr-16.
 */
public class Lender implements Parcelable {
    private int lenderID;
    private String lenderName;

    public Lender() {
    }

    public Lender(int lenderID, String lenderName) {
        this.lenderID = lenderID;
        this.lenderName = lenderName;
    }

    protected Lender(Parcel in) {
        lenderID = in.readInt();
        lenderName = in.readString();
    }

    public int getLenderID() {
        return lenderID;
    }

    public void setLenderID(int lenderID) {
        this.lenderID = lenderID;
    }

    public String getLenderName() {
        return lenderName;
    }

    public void setLenderName(String lenderName) {
        this.lenderName = lenderName;
    }

    public static final Creator<Lender> CREATOR = new Creator<Lender>() {
        @Override
        public Lender createFromParcel(Parcel in) {
            return new Lender(in);
        }

        @Override
        public Lender[] newArray(int size) {
            return new Lender[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(lenderID);
        dest.writeString(lenderName);
    }
}
