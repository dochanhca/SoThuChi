package com.example.anhlamrduc.sothuchi.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anhlamrduc.sothuchi.R;

/**
 * Created by AnhlaMrDuc on 14-Mar-16.
 */
public class AccountActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout for AccountActivity
        View v = inflater.inflate(R.layout.account_layout, container, false);

        return v;
    }
}
