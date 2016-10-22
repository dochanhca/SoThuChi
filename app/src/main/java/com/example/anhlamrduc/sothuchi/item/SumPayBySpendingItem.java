package com.example.anhlamrduc.sothuchi.item;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by AnhlaMrDuc on 06-May-16.
 */
public class SumPayBySpendingItem implements Parcelable {

    private int id;
    private String name;
    private Date payDate;
    private double sum;

    public SumPayBySpendingItem() {
    }

    public SumPayBySpendingItem(int id, String name, Date payDate, double sum) {
        this.id = id;
        this.name = name;
        this.payDate = payDate;
        this.sum = sum;
    }

    protected SumPayBySpendingItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        sum = in.readDouble();
    }

    public static final Creator<SumPayBySpendingItem> CREATOR = new Creator<SumPayBySpendingItem>() {
        @Override
        public SumPayBySpendingItem createFromParcel(Parcel in) {
            return new SumPayBySpendingItem(in);
        }

        @Override
        public SumPayBySpendingItem[] newArray(int size) {
            return new SumPayBySpendingItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeDouble(sum);
    }
}
