package com.jiangkang.ktools.requests.juejin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiangkang.ktools.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class JuejinFragment extends Fragment {


    public JuejinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_juejin, container, false);
    }

}
