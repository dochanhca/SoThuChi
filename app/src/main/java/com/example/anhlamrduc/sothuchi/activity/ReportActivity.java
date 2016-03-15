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
public class ReportActivity extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout for AccountActivity
        View v = inflater.inflate(R.layout.report_layout, container, false);

        return v;
    }
}
