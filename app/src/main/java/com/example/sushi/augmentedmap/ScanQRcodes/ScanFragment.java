package com.example.sushi.augmentedmap.ScanQRcodes;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sushi.augmentedmap.R;
import com.example.sushi.augmentedmap.ScanQRcodes.com.zbar.lib.CaptureActivity;

/**
 * Created by sushi on 01/04/15.
 */
public class ScanFragment extends Fragment {
    private View view;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        //view = inflater.inflate(R.layout.scan_layout, container, false);

        Intent intent = new Intent(this.getActivity(),CaptureActivity.class);
        startActivity(intent);

       return null;
    }
}

