package com.example.anhlamrduc.sothuchi.item;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by AnhlaMrDuc on 06-May-16.
 */
public class SumIncomeByReceiveItem implements Parcelable {
    private int id;
    private String name;
    private Date receiveDate;
    private double sum;

    public SumIncomeByReceiveItem() {
    }

    public SumIncomeByReceiveItem(int id, String name, Date receiveDate, double sum) {
        this.id = id;
        this.name = name;
        this.receiveDate = receiveDate;
        this.sum = sum;
    }

    protected SumIncomeByReceiveItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        sum = in.readDouble();
    }

    public static final Creator<SumIncomeByReceiveItem> CREATOR = new Creator<SumIncomeByReceiveItem>() {
        @Override
        public SumIncomeByReceiveItem createFromParcel(Parcel in) {
            return new SumIncomeByReceiveItem(in);
        }

        @Override
        public SumIncomeByReceiveItem[] newArray(int size) {
            return new SumIncomeByReceiveItem[size];
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

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
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
