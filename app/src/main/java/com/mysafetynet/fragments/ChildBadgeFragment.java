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
public class ChildBadgeFragment extends Fragment {


    public static ChildBadgeFragment newInstance() {
        ChildBadgeFragment fragment = new ChildBadgeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_child_badge, container, false);
    }

}
