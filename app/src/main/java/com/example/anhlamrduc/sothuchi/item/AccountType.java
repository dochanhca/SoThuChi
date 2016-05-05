package com.example.anhlamrduc.sothuchi.item;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AnhlaMrDuc on 20-Mar-16.
 */
public class AccountType implements Parcelable {

    private int typeID;
    private String typeName;
    private String image;

    protected AccountType(Parcel in) {
        typeID = in.readInt();
        typeName = in.readString();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(typeID);
        dest.writeString(typeName);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AccountType> CREATOR = new Creator<AccountType>() {
        @Override
        public AccountType createFromParcel(Parcel in) {
            return new AccountType(in);
        }

        @Override
        public AccountType[] newArray(int size) {
            return new AccountType[size];
        }
    };

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public AccountType() {
    }

    public AccountType(int typeID, String typeName, String image) {
        this.typeID = typeID;
        this.typeName = typeName;
        this.image = image;
    }
}
