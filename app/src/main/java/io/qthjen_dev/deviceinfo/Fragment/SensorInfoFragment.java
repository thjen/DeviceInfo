package io.qthjen_dev.deviceinfo.Fragment;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class SensorInfoFragment extends Fragment {

    private RecyclerView mRecycler;
    private InfoAdapter mAdapter;
    private List<String> mList = new LinkedList<>();

    private View mView;
    private SensorManager mSensorManager;

    public SensorInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_sensor_info, container, false);

        mRecycler = mView.findViewById(R.id.recyclerSensor);

        mList.clear();
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InfoAdapter(mList);

        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        //mList.add(mSensorManager.getSensorList(Sensor.TYPE_ALL) + "");
        mRecycler.setAdapter(mAdapter);

//        SensorManager oSM = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
//        List<Sensor> sensorsList = oSM.getSensorList(Sensor.TYPE_ALL);
//        for (Sensor s : sensorsList) {
//            //pw.write(s.toString() + "\n");
//            int bang = s.toString().indexOf("=");
//            int phay = s.toString().indexOf(",");
//            String sensor = s.toString().substring(bang + 2, phay - 1);
//            mList.add(getContext().getResources().getString(R.string.sensorname) + ": " + sensor + "\n");
//        }

        return mView;
    }

}
