package io.qthjen_dev.deviceinfo.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import io.qthjen_dev.deviceinfo.Adapter.InfoAdapter;
import io.qthjen_dev.deviceinfo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BetteryInfoFragment extends Fragment {

    private RecyclerView mRecyclerBattery;
    private InfoAdapter mAdapter;
    private List<String> mList = new LinkedList<>();

    private View mView;

    public BetteryInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_bettery_info, container, false);
        mRecyclerBattery = mView.findViewById(R.id.recyclerBettery);

        mList.clear();
        mRecyclerBattery.setHasFixedSize(true);
        mRecyclerBattery.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InfoAdapter(mList);

        mRecyclerBattery.setAdapter(mAdapter);

        batteryLevel(getContext());

        return mView;
    }

    public void batteryLevel(final Context context) {

        Intent intent  = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        int    level   = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        int    scale   = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
        int    percent = (level*100)/scale;
        int temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 1);
        int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 2);

        mList.add(getContext().getResources().getString(R.string.level)  + ": " + percent + "%" );
        mList.add(getContext().getResources().getString(R.string.temperature) + ": " + temp + " " + Html.fromHtml("&#176") + "C");
        mList.add(getContext().getResources().getString(R.string.temperature) + ": " + voltage  + " mV");

    }

}
