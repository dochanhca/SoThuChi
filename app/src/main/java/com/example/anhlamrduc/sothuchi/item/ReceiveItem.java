package com.example.anhlamrduc.sothuchi.item;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AnhlaMrDuc on 02-May-16.
 */
public class ReceiveItem implements Parcelable{
    private int receiveItemID;
    private String receiveItemName;
    private String description;

    public ReceiveItem() {
    }

    public ReceiveItem(int receiveItemID, String receiveItemName, String description) {
        this.receiveItemID = receiveItemID;
        this.receiveItemName = receiveItemName;
        this.description = description;
    }

    protected ReceiveItem(Parcel in) {
        receiveItemID = in.readInt();
        receiveItemName = in.readString();
        description = in.readString();
    }

    public static final Creator<ReceiveItem> CREATOR = new Creator<ReceiveItem>() {
        @Override
        public ReceiveItem createFromParcel(Parcel in) {
            return new ReceiveItem(in);
        }

        @Override
        public ReceiveItem[] newArray(int size) {
            return new ReceiveItem[size];
        }
    };

    public int getReceiveItemID() {
        return receiveItemID;
    }

    public void setReceiveItemID(int receiveItemID) {
        this.receiveItemID = receiveItemID;
    }

    public String getReceiveItemName() {
        return receiveItemName;
    }

    public void setReceiveItemName(String receiveItemName) {
        this.receiveItemName = receiveItemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(receiveItemID);
        dest.writeString(receiveItemName);
        dest.writeString(description);
    }
}
