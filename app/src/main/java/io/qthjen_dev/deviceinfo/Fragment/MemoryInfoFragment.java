package io.qthjen_dev.deviceinfo.Fragment;


import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import io.qthjen_dev.deviceinfo.Adapter.InfoAdapter;
import io.qthjen_dev.deviceinfo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemoryInfoFragment extends Fragment {

    private TextView mTvMemTotal;
    private TextView mTvMemAvail;
    private RecyclerView mRecyclerAllProcess;
    private RadioButton mRadioProcess;
    private RadioButton mRadioService;
    private RadioGroup mRadioGroup;
    private TextView mTv_show;

    private List<String> mListProcess = new LinkedList<>();
    private List<String> mListService = new LinkedList<>();
    private List<String> mListPidProcess = new LinkedList<>();
    private InfoAdapter mAdapter;

    private boolean IS_SHOW = false;

    private View mViewRoot;

    public MemoryInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mViewRoot = inflater.inflate(R.layout.fragment_memory, container, false);

        mTvMemTotal = mViewRoot.findViewById(R.id.memtotal);
        mTvMemAvail = mViewRoot.findViewById(R.id.memavail);
        mRecyclerAllProcess = mViewRoot.findViewById(R.id.recyclerAllProcess);
        mRadioGroup = mViewRoot.findViewById(R.id.radio_group);
        mRadioProcess = mViewRoot.findViewById(R.id.radio_process);
        mRadioService = mViewRoot.findViewById(R.id.radio_service);
        mTv_show = mViewRoot.findViewById(R.id.tv_show);

        final ActivityManager activityManager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        long totalMemory = memoryInfo.totalMem;
        String strTotalMem = new DecimalFormat("#.##").format(((double) (totalMemory/1024)/1024)/1024 );

        mTvMemTotal.setText(getMyResource(R.string.totalram) + ": " + strTotalMem  + " GB" );

        RamAvail(60000, 500);

        if (!IS_SHOW) {

            mTv_show.setText(getMyResource(R.string.listprocess));
            mListProcess.clear();
            mAdapter = new InfoAdapter(mListProcess);
            mRecyclerAllProcess.setHasFixedSize(true);
            mRecyclerAllProcess.setLayoutManager(new LinearLayoutManager(getContext()));

            mRecyclerAllProcess.setAdapter(mAdapter);

            ActivityManager activityManager1 = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager1.getRunningAppProcesses();

            PackageManager packageManager = getContext().getPackageManager();
            ApplicationInfo applicationInfo;

            for ( ActivityManager.RunningAppProcessInfo runningAppProcessInfo : processInfos ) {

                try {
                    applicationInfo = packageManager.getApplicationInfo(runningAppProcessInfo.processName, 0);
                } catch (final PackageManager.NameNotFoundException e) {
                    applicationInfo = null;
                }
                mListProcess.add((String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : runningAppProcessInfo.processName));

            }

        } else {
            mTv_show.setText(getMyResource(R.string.listprocess));
            mListProcess.clear();
            mAdapter = new InfoAdapter(mListProcess);
            mRecyclerAllProcess.setHasFixedSize(true);
            mRecyclerAllProcess.setLayoutManager(new LinearLayoutManager(getContext()));

            mRecyclerAllProcess.setAdapter(mAdapter);

            ActivityManager activityManager1 = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager1.getRunningAppProcesses();

            PackageManager packageManager = getContext().getPackageManager();
            ApplicationInfo applicationInfo;

            for ( ActivityManager.RunningAppProcessInfo runningAppProcessInfo : processInfos ) {

                try {
                    applicationInfo = packageManager.getApplicationInfo(runningAppProcessInfo.processName, 0);
                } catch (final PackageManager.NameNotFoundException e) {
                    applicationInfo = null;
                }
                mListProcess.add((String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : runningAppProcessInfo.processName));

            }

            mRecyclerAllProcess.setVisibility(View.VISIBLE);
        }

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch ( checkedId ) {

                    case R.id.radio_process:

                        IS_SHOW = true;
                        mTv_show.setText(getMyResource(R.string.listprocess));
                        mListProcess.clear();
                        mAdapter = new InfoAdapter(mListProcess);
                        mRecyclerAllProcess.setHasFixedSize(true);
                        mRecyclerAllProcess.setLayoutManager(new LinearLayoutManager(getContext()));

                        mRecyclerAllProcess.setAdapter(mAdapter);

                        ActivityManager activityManager1 = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
                        List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager1.getRunningAppProcesses();

                        PackageManager packageManager = getContext().getPackageManager();
                        ApplicationInfo applicationInfo;

                        for ( ActivityManager.RunningAppProcessInfo runningAppProcessInfo : processInfos ) {

                            try {
                                applicationInfo = packageManager.getApplicationInfo(runningAppProcessInfo.processName, 0);
                            } catch (final PackageManager.NameNotFoundException e) {
                                applicationInfo = null;
                            }
                            mListProcess.add((String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : runningAppProcessInfo.processName));

                        }

                        mRecyclerAllProcess.setVisibility(View.VISIBLE);

                        break;

                    case R.id.radio_service:

                        mListService.clear();
                        IS_SHOW = true;
                        mTv_show.setText(getMyResource(R.string.listservice));
                        mListService.clear();
                        mAdapter = new InfoAdapter(mListService);
                        mRecyclerAllProcess.setHasFixedSize(true);
                        mRecyclerAllProcess.setLayoutManager(new LinearLayoutManager(getContext()));

                        mRecyclerAllProcess.setAdapter(mAdapter);

                        ActivityManager manager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
                        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                            //String serv = service.service.getClassName();
                            mListService.add(service.service.getClassName());
                        }

                        mRecyclerAllProcess.setVisibility(View.VISIBLE);

                        break;

                }

            }
        });

        return mViewRoot;
    }

    private void RamAvail(final long finish, final long tick) {

        CountDownTimer countDownTimer = new CountDownTimer(finish, tick) {
            @Override
            public void onTick(long millisUntilFinished) {

                ActivityManager myactivityManager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
                ActivityManager.MemoryInfo mymemoryInfo = new ActivityManager.MemoryInfo();
                myactivityManager.getMemoryInfo(mymemoryInfo);

                long availableMem = (mymemoryInfo.availMem)/0x100000L;
                double percentAvail = ((double) mymemoryInfo.availMem / (double) mymemoryInfo.totalMem)*100;
                String strparcentAvail = new DecimalFormat("#.##").format(percentAvail);
                mTvMemAvail.setText(getMyResource(R.string.availableram) + ": " + availableMem + " MB "
                        + "(" + strparcentAvail + "%" + ")");

            }

            @Override
            public void onFinish() {

                RamAvail(finish, tick);
                cancel();

            }
        }.start();

    }

    private String getMyResource(int mystring) {

        return getResources().getString(mystring);
    }
/*
    class ProcessAdapter extends RecyclerView.Adapter<ProcessAdapter.ViewHolder> {

        private List<String> list;

        public ProcessAdapter(List<String> list) {

            this.list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.process_layout, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.tvNameProcess.setText(list.get(position));

            holder.btKill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvNameProcess;
            Button btKill;

            public ViewHolder(View itemView) {
                super(itemView);

                tvNameProcess = itemView.findViewById(R.id.nameApp);
                btKill = itemView.findViewById(R.id.bt_kill);

            }

        }

    }
*/

}
