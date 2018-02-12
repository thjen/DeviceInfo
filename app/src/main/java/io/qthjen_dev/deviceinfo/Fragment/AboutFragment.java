package io.qthjen_dev.deviceinfo.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.qthjen_dev.deviceinfo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    private View mView;
    private TextView about, aboutLine2, aboutLine3, aboutLine4;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_about, container, false);

        about = mView.findViewById(R.id.about);
        aboutLine2 = mView.findViewById(R.id.aboutline2);
        aboutLine3 = mView.findViewById(R.id.aboutline3);
        aboutLine4 = mView.findViewById(R.id.aboutline4);

        about.setText(getContext().getResources().getString(R.string.line1));
        aboutLine2.setText(getContext().getResources().getString(R.string.line2));
        aboutLine3.setText(getContext().getResources().getString(R.string.line3));
        aboutLine4.setText(getContext().getResources().getString(R.string.line4));

        return mView;
    }

}
