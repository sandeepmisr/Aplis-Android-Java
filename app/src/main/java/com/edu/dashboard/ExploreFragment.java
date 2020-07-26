package com.edu.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.edu.aplis.R;

public class ExploreFragment extends Fragment {
    public Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.explore_fragment, container, false);

        context = getActivity();
        return rootView;
    }
}
