package io.qthjen_dev.deviceinfo.Fragment;

import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import io.qthjen_dev.deviceinfo.Adapter.InfoAdapter;
import io.qthjen_dev.deviceinfo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CpuInfoFragment extends Fragment implements GLSurfaceView.Renderer {

    private RecyclerView mRecyclerCpu;
    private InfoAdapter mInfoAdapter;
    private List<String> mListInfoCpu = new LinkedList<>();
    private View mViewRoot;
    private TextView mTv_cpuUsage, mTv_gpu, tv_renderer, tv_versionGl;
    private RelativeLayout mRoot;
    private GLSurfaceView mGLSurfaceView;
    private String mVendor, renderer, version;

    public CpuInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewRoot = inflater.inflate(R.layout.fragment_cpu_info, container, false);

        mRecyclerCpu = mViewRoot.findViewById(R.id.recyclerCpu);
        mTv_cpuUsage = mViewRoot.findViewById(R.id.tv_cpuUsage);
        mTv_gpu = mViewRoot.findViewById(R.id.tv_opengl);
        mRoot = mViewRoot.findViewById(R.id.rootSoc);
        tv_renderer = mViewRoot.findViewById(R.id.tv_renderer);
        tv_versionGl = mViewRoot.findViewById(R.id.version);

        mGLSurfaceView = new GLSurfaceView(getActivity());
        mGLSurfaceView.setRenderer(this);
        mRoot.addView(mGLSurfaceView);

        mListInfoCpu.clear();
        mRecyclerCpu.setHasFixedSize(true);
        mRecyclerCpu.setLayoutManager(new LinearLayoutManager(getContext()));

        mInfoAdapter = new InfoAdapter(mListInfoCpu);
        mRecyclerCpu.setAdapter(mInfoAdapter);

        mListInfoCpu.add(getMyResource(R.string.cpuname) + ": " + getCpuInfo("model_name"));
        mListInfoCpu.add(getMyResource(R.string.cpucore) + ": " + getCpuInfo("cpu_cores"));
        mListInfoCpu.add(getMyResource(R.string.vendorid) + ": " + getCpuInfo("vendor_id"));
        mListInfoCpu.add(getMyResource(R.string.cpumodel) + ": " + getCpuInfo("model"));
        mListInfoCpu.add(getMyResource(R.string.cachesize) + ": " + getCpuInfo("cache_size"));

//        new GetInfoCpu().execute("model_name", getMyResource(R.string.cpuname));
//        new GetInfoCpu().execute("cpu_cores", getMyResource(R.string.cpucore));
//        new GetInfoCpu().execute("vendor_id", getMyResource(R.string.vendorid));
//        new GetInfoCpu().execute("model", getMyResource(R.string.cpumodel));
//        new GetInfoCpu().execute("cache_size", getMyResource(R.string.cachesize));

//        String cpuin = getCpuInfo("cpu_MHz").trim().toString();
//        if ( cpuin == "" || cpuin == null) {
//            mListInfoCpu.add(getMyResource(R.string.cpumhz) + ": " + "Unknown");
//        } else {
//            double cpumhz = Double.parseDouble(cpuin) / (double) 1024;
//            String strcpu = new DecimalFormat("#.##").format(cpumhz);
//            mListInfoCpu.add(getMyResource(R.string.cpumhz) + ": " + strcpu + " GHz");
//        }

        //getGetCpuAvail();

        return mViewRoot;

    }

    private void getGetCpuAvail() {

        CountDownTimer countDownTimer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                try {

                    Process process = Runtime.getRuntime().exec("mpstat");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String reader;
                    while ( (reader = bufferedReader.readLine()) != null ) {

                        String line0 = bufferedReader.readLine();
                        String line1 = bufferedReader.readLine();
                        String line2 = bufferedReader.readLine();
                        //Log.d("line2", line2 + "");
                        mTv_cpuUsage.setText(getMyResource(R.string.cpuload) + ": " + line2.substring(line2.length() - 5, line2.length()) + "%");

                        mInfoAdapter.notifyDataSetChanged();

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFinish() {

                getGetCpuAvail();
                cancel();
            }

        }.start();

    }

    private String getCpuInfo(String key) {

        try {
            //Process process = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            //BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader reader = new BufferedReader(new FileReader("/proc/cpuinfo"));
            String data;
            while ( (data = reader.readLine()) != null ) {

                //Log.d("data", data + "");
                String datasplit[] = data.split(":"); // split tách chuỗi thành 2 chuỗi 2 bên regex(":")
                // process: 0 => tách thành process và 0;

                if ( datasplit.length > 1) {

                    String key_split = datasplit[0].trim().replace(" ", "_");
                    String value = datasplit[1].trim();
                    if ( key.equals(key_split)) {
                        return value.replaceAll("\\s+", " ");
                    }

                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "unknown";

    }

    private String getMyResource(int mystring) {

        return getResources().getString(mystring);
    }

    @Override
    public void onSurfaceCreated(final GL10 gl, EGLConfig config) {

        mVendor = gl.glGetString(GL10.GL_VENDOR);
        renderer = gl.glGetString(GL10.GL_RENDERER);
        version = gl.glGetString(GL10.GL_VERSION);
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                mTv_gpu.setText(getMyResource(R.string.gpu) + ": " + mVendor);
                tv_renderer.setText(getMyResource(R.string.renderer) + ": " + renderer);
                tv_versionGl.setText(getMyResource(R.string.versiongl) + ": "  + version);
                mRoot.removeView(mGLSurfaceView);
            }
        });

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }

//    class Wrapper {
//
//        String key = "UNDEFINED";
//        String name = "UNDEFINED";
//
//    }

//    class GetInfoCpu extends AsyncTask<String, Void, Wrapper> {
//
//        @Override
//        protected Wrapper doInBackground(String... strings) {
//
//            Wrapper wrapper = new Wrapper();
//
//            //List<String> list = new ArrayList<>();
//            //list.clear();
//            try {
//                //Process process = Runtime.getRuntime().exec("cat /proc/cpuinfo");
//                //BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//                BufferedReader reader = new BufferedReader(new FileReader("/proc/cpuinfo"));
//                String data;
//                while ( (data = reader.readLine()) != null ) {
//
//                    //Log.d("data", data + "");
//                    String datasplit[] = data.split(":"); // split tách chuỗi thành 2 chuỗi 2 bên regex(":")
//                    // process: 0 => tách thành process và 0;
//
//                    if ( datasplit.length > 1) {
//
//                        String key_split = datasplit[0].trim().replace(" ", "_");
//                        String value = datasplit[1].trim();
//                        if ( strings[0].equals(key_split)) {
//                            //list.add(value.replaceAll("\\s+", " "));
//                            //list.add(strings[1]);
//                            wrapper.key = value.replaceAll("\\s+", " ");
//                            wrapper.name = strings[1];
//                            return wrapper;
//                        }
//
//                    }
//
//                }
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Wrapper wrapper) {
//            //mListInfoCpu.add(list.get(1).toString() + ": " + list.get(0).toString());
//            mListInfoCpu.add(wrapper.name + ": " + wrapper.key);
//            super.onPostExecute(wrapper);
//        }
//
//    }

}
