package com.example.anhlamrduc.sothuchi.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.anhlamrduc.sothuchi.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 21-Apr-16.
 */
public class TripFragment extends Fragment {

    private OnDataPassFromTrip onDataPassFromTrip;

    public interface OnDataPassFromTrip {
        public void onReceivedDataFromTrip(String trip);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onDataPassFromTrip = (OnDataPassFromTrip) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_trip, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.txt_done)
    public void selectReceiverDone() {
        String trip = edtTrip.getText().toString();
        onDataPassFromTrip.onReceivedDataFromTrip(trip);
    }

    @OnClick(R.id.txt_cancel)
    public void goToBack() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
    }

    @Bind(R.id.edt_trip)
    EditText edtTrip;

}
