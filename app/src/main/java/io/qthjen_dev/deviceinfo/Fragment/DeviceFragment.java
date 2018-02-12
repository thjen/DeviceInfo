package io.qthjen_dev.deviceinfo.Fragment;


import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.qthjen_dev.deviceinfo.Adapter.InfoAdapter;
import io.qthjen_dev.deviceinfo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceFragment extends Fragment {

    private RecyclerView mRecyclerDevice;
    private View mRootView;

    private List<String> mList = new ArrayList<>();
    private InfoAdapter mAdapter;

    public DeviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_device, container, false);

        mRecyclerDevice = mRootView.findViewById(R.id.recyclerDevice);

        mList.clear();

        mRecyclerDevice.setHasFixedSize(true);
        mRecyclerDevice.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new InfoAdapter(mList);

        mRecyclerDevice.setAdapter(mAdapter);

        /** add data **/
        mList.add(getMyResource(R.string.model) + ": " + Build.MODEL);
        mList.add(getMyResource(R.string.hardware) + ": " + Build.HARDWARE);
        mList.add(getMyResource(R.string.brand) + ": " + Build.BRAND);
        mList.add(getMyResource(R.string.board) + ": " + Build.BOARD);
        mList.add(getMyResource(R.string.manufacture) + ": " + Build.MANUFACTURER);

        // get screen size inches
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels + (int) pxFromDp(statusBarHeight(getResources()));
        double wi = (double) width/ (double) displayMetrics.xdpi;
        double hei = (double) height/ (double) displayMetrics.ydpi;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hei, 2);
        double screenin = Math.sqrt(x + y);
        String screeninches = new DecimalFormat("#.##").format(screenin);

        mList.add(getMyResource(R.string.screensize) + ": " + screeninches + " inches");
        mList.add(getMyResource(R.string.screenre) + ": " + String.valueOf(width) + " x " +
                String.valueOf(height) + " pixel");

        int densityDpi = (int) (displayMetrics.density * 160f);

        mList.add(getMyResource(R.string.screenden) + ": " + String.valueOf(densityDpi));

        getAvailableInternalMemorySize();

        return mRootView;
    }

    public void getAvailableInternalMemorySize() {

        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        long total = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            total = stat.getTotalBytes();
        }

        mList.add(getMyResource(R.string.internalStorage) + ": " + bytesToHuman(total));
        mList.add(getMyResource(R.string.availStorage) + ": " + bytesToHuman(availableBlocks * blockSize) + " (" + new DecimalFormat("#.##").format(((( (double) availableBlocks * (double) blockSize)/(double) total)*100)) + "%)");

    }

    public static String floatForm (double d) {
        return new DecimalFormat("#.##").format(d);
    }

    public static String bytesToHuman(long size) {

        long Kb = 1  * 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;

        if (size <  Kb)                 return floatForm(        size     ) + " byte";
        if (size >= Kb && size < Mb)    return floatForm((double)size / Kb) + " KB";
        if (size >= Mb && size < Gb)    return floatForm((double)size / Mb) + " MB";
        if (size >= Gb && size < Tb)    return floatForm((double)size / Gb) + " GB";
        if (size >= Tb && size < Pb)    return floatForm((double)size / Tb) + " TB";
        if (size >= Pb && size < Eb)    return floatForm((double)size / Pb) + " PB";
        if (size >= Eb)                 return floatForm((double)size / Eb) + " EB";

        return "???";
    }

    public float dpFromPx(final float px) {
        return px / getResources().getDisplayMetrics().density;
    }

    public float pxFromDp( final float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }

    private static int statusBarHeight(android.content.res.Resources res) {
        return (int) (24 * res.getDisplayMetrics().density);
    }

    private String getMyResource(int mystring) {

        return getResources().getString(mystring);
    }

}
