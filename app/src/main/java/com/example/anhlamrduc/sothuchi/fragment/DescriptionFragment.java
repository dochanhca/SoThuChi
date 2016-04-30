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
 * Created by AnhlaMrDuc on 19-Apr-16.
 */
public class DescriptionFragment extends Fragment {
    OnDataPassDataFromDescription onDataPassDataFromDescription;

    public interface OnDataPassDataFromDescription {
        public void onReceivedDataFromDescription(String description);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onDataPassDataFromDescription = (OnDataPassDataFromDescription) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_description, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.txt_cancel)
    public void goToBack() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            }
    }

    @OnClick(R.id.txt_done)
    public void sendDesciptionToNote() {
        String desciption = edtDescription.getText().toString();
        onDataPassDataFromDescription.onReceivedDataFromDescription(desciption);
    }

    @Bind(R.id.edt_description)
    EditText edtDescription;
}
