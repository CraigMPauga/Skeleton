package com.example.craigpauga.reality.OpeningActivity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.craigpauga.reality.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class gettingStartedFragment extends Fragment {


    public gettingStartedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_getting_started, container, false);
    }

}
