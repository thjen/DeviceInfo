package io.qthjen_dev.deviceinfo.Fragment;

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import io.qthjen_dev.deviceinfo.Adapter.InfoAdapter;
import io.qthjen_dev.deviceinfo.R;
import io.qthjen_dev.deviceinfo.Utils.RootUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SystemInfoFragment extends Fragment {

    private View mRootView;

    private RecyclerView mRecyclerSystem;
    private List<String> mListInfo = new LinkedList<>();
    private InfoAdapter mAdapter;
    private TextView tvTimer;

    public SystemInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_system_info, container, false);

        mRecyclerSystem = mRootView.findViewById(R.id.recyclerSystem);
        tvTimer = mRootView.findViewById(R.id.tv_uptime);
        mAdapter = new InfoAdapter(mListInfo);

        mListInfo.clear();
        mRecyclerSystem.setAdapter(mAdapter);

        mRecyclerSystem.setHasFixedSize(true);
        mRecyclerSystem.setLayoutManager(new LinearLayoutManager(getContext()));

        /** add data **/
        mListInfo.add(getMyResource(R.string.androidversion) + ": " + Build.VERSION.RELEASE);
        mListInfo.add(getMyResource(R.string.apiLevel) + ": " + Build.VERSION.SDK);
        mListInfo.add(getMyResource(R.string.bootloader) + ": " + Build.BOOTLOADER);
        mListInfo.add(getMyResource(R.string.buildid) + ": " + Build.DISPLAY);

//        try {
//            mListInfo.add(getMyResource(R.string.kernelAr) + ": " + ProcessCommandline("uname -m").readLine());
//            mListInfo.add(getMyResource(R.string.kernelVer) + ": " + ProcessCommandline("uname -r").readLine() + " "
//                    + Build.VERSION.INCREMENTAL);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        mListInfo.add(getMyResource(R.string.opengl) + ": " + String.valueOf(getVersionFromPackageManager(getContext())) + ".0");
        mListInfo.add(getMyResource(R.string.javavm) + ": " + String.valueOf(System.getProperty("java.vm.version")));

        if ( RootUtils.isDeviceRooted() ) {
            mListInfo.add(getMyResource(R.string.rootAccess) + ": " + "Yes");
        } else {
            mListInfo.add(getMyResource(R.string.rootAccess) + ": " + "No");
        }

        new GetKernelInfo().execute("uptime");

        return mRootView;
    }

    /** setup uptime **/
    /*private void StartTimer(final long finish, final long tick) {

        final CountDownTimer countDownTimer = new CountDownTimer(finish, tick) {
            @Override
            public void onTick(long millisUntilFinished) {

                try {
                    String time = ProcessCommandline("uptime").readLine();
                    int index1 = time.indexOf(":");
                    int index2 = time.indexOf(",");
                    String timevalue = time.substring(index1 + 2, index2);
                    tvTimer.setText(getMyResource(R.string.systemuptime) + ": " + timevalue);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFinish() {

                StartTimer(finish, tick);
                cancel();

            }
        }.start();

    }
*/
    /** ----------------------------------------------------------------------------------- **/
/*
    private BufferedReader ProcessCommandline(String cmd) {

        try {
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return reader;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
*/
    /** get version opengl es **/
    private static int getVersionFromPackageManager(Context context) {
        PackageManager packageManager = context.getPackageManager();
        FeatureInfo[] featureInfos = packageManager.getSystemAvailableFeatures();
        if (featureInfos != null && featureInfos.length > 0) {
            for (FeatureInfo featureInfo : featureInfos) {
                // Null feature name means this feature is the open gl es version feature.
                if (featureInfo.name == null) {
                    if (featureInfo.reqGlEsVersion != FeatureInfo.GL_ES_VERSION_UNDEFINED) {
                        return getMajorVersion(featureInfo.reqGlEsVersion);
                    } else {
                        return 1; // Lack of property means OpenGL ES version 1
                    }
                }
            }
        }
        return 1;
    }

    private static int getMajorVersion(int glEsVersion) {
        return ((glEsVersion & 0xffff0000) >> 16);
    }

    /** ------------------------------------------------------------------------------- **/

    private String getMyResource(int mystring) {

        return getContext().getResources().getString(mystring);
    }

    class GetKernelInfo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            try {

                Process process = Runtime.getRuntime().exec(strings[0]);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                return reader.readLine().toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "Unknown";
        }

        @Override
        protected void onPostExecute(final String s) {
            super.onPostExecute(s);

            final CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    int index1 = s.indexOf(":");
                    int index2 = s.indexOf(",");
                    String timevalue = s.substring(index1 + 2, index2 - 3);
                    tvTimer.setText(getMyResource(R.string.systemuptime) + ": " + timevalue);

                }

                @Override
                public void onFinish() {

                    new GetKernelInfo().execute("uptime");
                    cancel();

                }
            }.start();


        }

    }

}
