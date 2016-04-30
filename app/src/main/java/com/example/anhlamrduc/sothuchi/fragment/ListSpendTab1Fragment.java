package com.example.anhlamrduc.sothuchi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.anhlamrduc.sothuchi.R;

/**
 * Created by AnhlaMrDuc on 29-Mar-16.
 */
public class ListSpendTab1Fragment extends Fragment {

    ImageView imgView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_list_spend_tab_one, container, false);
        imgView = (ImageView) view.findViewById(R.id.img_test);
        return view;
    }
}
