package com.example.anhlamrduc.sothuchi.fragment;

import android.widget.EditText;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 21-Apr-16.
 */
public class TripFragment extends BaseFragment {

    @Override
    protected int layoutID() {
        return R.layout.fr_trip;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void handleData() {

    }

    @OnClick(R.id.txt_done)
    public void selectReceiverDone() {
        String trip = edtTrip.getText().toString();
        getEditor().putString(MainActivity.TRIP_OR_EVENT, trip);
        getEditor().commit();
        getFragmentManager().popBackStack();

    }

    @OnClick(R.id.txt_cancel)
    public void goToBack() {
        getFragmentManager().popBackStack();
    }

    @Bind(R.id.edt_trip)
    EditText edtTrip;

}
