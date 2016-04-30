package com.example.anhlamrduc.sothuchi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 28-Mar-16.
 */
public class AddSpendItemFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fr_add_spend_item, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.txt_cancel)
    public void goToBack() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            return;
        }
    }

    @Bind(R.id.txt_cancel)
    TextView txtCancel;

}
