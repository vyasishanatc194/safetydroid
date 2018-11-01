package com.mysafetynet.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mysafetynet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChildRequestFragment extends Fragment {


    public static ChildRequestFragment newInstance() {
        ChildRequestFragment fragment = new ChildRequestFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_child_request, container, false);
    }

}
