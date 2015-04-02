package com.example.sushi.augmentedmap;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by sushi on 01/04/15.
 */
public class HistoryFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.history_layout, container, false);

        return rootView;

    }
}