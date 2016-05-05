package com.example.anhlamrduc.sothuchi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.asynctask.DBGetAllAccount;
import com.example.anhlamrduc.sothuchi.asynctask.DBGetAllReceiveItem;
import com.example.anhlamrduc.sothuchi.asynctask.DBGetAllSpendingItem;
import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.ReceiveItem;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 02-May-16.
 */
public class SplashActivity extends AppCompatActivity {
    private static final int TIME_DELAY = 2000;
    ArrayList<SpendingItem> listSpendingItem = new ArrayList<>();
    ArrayList<ReceiveItem> listReceiveItem = new ArrayList<>();
    private ArrayList<Account> listAccount = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DBGetAllAccount dbGetAllAccount = new DBGetAllAccount(this) {
            @Override
            protected void onPostExecute(ArrayList<Account> accounts) {
                listAccount = accounts;
            }
        };
        dbGetAllAccount.execute();

        DBGetAllSpendingItem dbGetAllSpendingItem = new DBGetAllSpendingItem(this) {
            @Override
            protected void onPostExecute(ArrayList<SpendingItem> spendingItems) {
                listSpendingItem = spendingItems;
            }
        };
        dbGetAllSpendingItem.execute();
        DBGetAllReceiveItem dbGetAllReceiveItem = new DBGetAllReceiveItem(this) {
            @Override
            protected void onPostExecute(ArrayList<ReceiveItem> receiveItems) {
                super.onPostExecute(receiveItems);
                listReceiveItem = receiveItems;
                MainActivity.startActivity(SplashActivity.this, listReceiveItem, listSpendingItem, listAccount);
                finish();
            }
        };
        dbGetAllReceiveItem.execute();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                    finish();
//            }
//        }, TIME_DELAY);
    }
}
