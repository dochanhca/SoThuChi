package com.example.anhlamrduc.sothuchi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AnhlaMrDuc on 25-Mar-16.
 */
public class AddAccountFragment extends Fragment {

    private static final String ADD_ACCOUNT = "add account fragment: ";

    @Bind(R.id.txt_save)
    TextView txtSave;
    @Bind(R.id.edt_amount)
    EditText edtAmount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = (View) inflater.inflate(R.layout.fragment_add_account, container, false);
        ButterKnife.bind(this, v);
        Log.e(ADD_ACCOUNT, "add account created");
        return v;

    }
}
