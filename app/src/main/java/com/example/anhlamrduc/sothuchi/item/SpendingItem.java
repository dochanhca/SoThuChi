package com.example.anhlamrduc.sothuchi.item;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AnhlaMrDuc on 01-Apr-16.
 */
public class SpendingItem implements Parcelable {

    private int spendingItemID;
    private String spendingItemName;
    private String note;
    private String parentItem;

    public int getSpendingItemID() {
        return spendingItemID;
    }

    public void setSpendingItemID(int spendingItemID) {
        this.spendingItemID = spendingItemID;
    }

    public String getSpendingItemName() {
        return spendingItemName;
    }

    public void setSpendingItemName(String spendingItemName) {
        this.spendingItemName = spendingItemName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getParentItem() {
        return parentItem;
    }

    public void setParentItem(String parentItem) {
        this.parentItem = parentItem;
    }

    public SpendingItem() {
    }

    public SpendingItem(int spendingItemID, String spendingItemName, String note, String parentItem) {
        this.spendingItemID = spendingItemID;
        this.spendingItemName = spendingItemName;
        this.note = note;
        this.parentItem = parentItem;
    }

    protected SpendingItem(Parcel in) {
        spendingItemID = in.readInt();
        spendingItemName = in.readString();
        note = in.readString();
        parentItem = in.readString();
    }

    public static final Creator<SpendingItem> CREATOR = new Creator<SpendingItem>() {
        @Override
        public SpendingItem createFromParcel(Parcel in) {
            return new SpendingItem(in);
        }

        @Override
        public SpendingItem[] newArray(int size) {
            return new SpendingItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(spendingItemID);
        dest.writeString(spendingItemName);
        dest.writeString(note);
        dest.writeString(parentItem);
    }
}
