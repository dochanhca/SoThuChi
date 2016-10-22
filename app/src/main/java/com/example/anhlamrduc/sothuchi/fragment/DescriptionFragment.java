package com.example.anhlamrduc.sothuchi.fragment;

import android.widget.EditText;

import com.example.anhlamrduc.sothuchi.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 19-Apr-16.
 */
public class DescriptionFragment extends BaseFragment {

    @Override
    protected int layoutID() {
        return R.layout.fr_description;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void handleData() {

    }

    @OnClick(R.id.txt_cancel)
    public void goToBack() {
        getFragmentManager().popBackStack();
    }

    @OnClick(R.id.txt_done)
    public void sendDesciptionToNote() {
        String desciption = edtDescription.getText().toString();
        setDescription(desciption);
        getFragmentManager().popBackStack();
    }

    @Bind(R.id.edt_description)
    EditText edtDescription;
}
