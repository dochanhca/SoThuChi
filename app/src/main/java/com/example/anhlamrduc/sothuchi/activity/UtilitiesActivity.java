package com.example.anhlamrduc.sothuchi.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anhlamrduc.sothuchi.R;

/**
 * Created by AnhlaMrDuc on 14-Mar-16.
 */
public class UtilitiesActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout for AccountActivity
        View v = inflater.inflate(R.layout.utilities_layout, container, false);

        return v;
    }
}