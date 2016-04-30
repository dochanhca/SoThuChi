package com.example.anhlamrduc.sothuchi.fragment;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.adapter.ListReceiverApdater;
import com.example.anhlamrduc.sothuchi.item.Receiver;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 21-Apr-16.
 */
public class ReceiverFragment extends Fragment {
    private ArrayList<Receiver> arrReceiver;
    private ListReceiverApdater listReceiverApdater;

    onDataPassFromReceiver onDataPassFromReceiver;

    public interface onDataPassFromReceiver {
        public void onReceivedDataFromReceiver(String receiver);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String receiver = arrReceiver.get(position).getReceiverName();
            edtReceiver.setText(receiver);
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onDataPassFromReceiver = (ReceiverFragment.onDataPassFromReceiver) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Receive a Bundle from main activity
        arrReceiver = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_MAIN);

        View v = inflater.inflate(R.layout.fr_receiver, container, false);
        ButterKnife.bind(this, v);
        edtReceiver.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);

        listReceiverApdater = new ListReceiverApdater(getContext(), arrReceiver);
        lvReceiver.setAdapter(listReceiverApdater);
        lvReceiver.setOnItemClickListener(onItemClickListener);

        return v;
    }

    @OnClick(R.id.txt_done)
    public void selectReceiverDone() {
        String receiver = edtReceiver.getText().toString();
        onDataPassFromReceiver.onReceivedDataFromReceiver(receiver);
    }

    @OnClick(R.id.txt_cancel)
    public void goToBack() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
    }

    @Bind(R.id.edt_receiver)
    EditText edtReceiver;
    @Bind(android.R.id.list)
    ListView lvReceiver;
}
